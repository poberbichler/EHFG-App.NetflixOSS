package org.ehfg.app.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.runAsync;

/**
 * @author patrick
 * @since 06.2016
 */
@Service
class SearchServiceImpl implements SearchService {
	private static final String TWITTER_URL = "http://EHFGAPP-TWITTER";
	private static final String PROGRAM_URL = "http://EHFGAPP-PROGRAM";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final RestTemplate restTemplate;

	@Autowired
	public SearchServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public SearchResultRepresentation findBy(String input) {
		try {
			logger.info("searching for [{}], calling the endpoint", input);

			CompletableFuture<String> inputFuture = completedFuture(input);
			CompletableFuture<SearchResult> twitter = inputFuture.thenApplyAsync(this::searchTwitter);
			CompletableFuture<Map<ResultType, List<SearchResultItem>>> program = inputFuture.thenApplyAsync(this::searchProgram);

			return twitter.thenCombineAsync(program, this::combine).get(5, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			logger.error("an exception occured when searching for [{}]", input, e);
			return SearchResult.empty();
		}
	}

	private Map<ResultType, List<SearchResultItem>> searchProgram(String input) {
		return restTemplate.exchange(PROGRAM_URL + "/search/{input}", HttpMethod.GET, null,
				new ParameterizedTypeReference<Map<ResultType, List<SearchResultItem>>>() {
				}, input).getBody();
	}

	private SearchResult searchTwitter(String input) {
		return restTemplate.getForObject(TWITTER_URL + "/search/{input}", SearchResult.class, input);
	}

	private SearchResult combine(SearchResult twitterResult, Map<ResultType, List<SearchResultItem>> programResult) {
		List<SearchResult.SearchResultData> programData = new ArrayList<>();
		for (Map.Entry<ResultType, List<SearchResultItem>> entry : programResult.entrySet()) {
			programData.add(new SearchResult.SearchResultData(entry.getKey(), entry.getValue()));
		}

		return new SearchResult(twitterResult.getTweets(), programData);
	}

	@Override
	public void refreshIndex() {
		logger.info("updating indexes...");

		runAsync(this::updateProgramIndex)
				.runAfterBothAsync(runAsync(this::updateTwitterIndex), () -> logger.info("updated both search indexes"))
				.exceptionally(ex -> {
					logger.info("an error occured while updating indexes", ex);
					return null;
				});
	}

	private void updateTwitterIndex() {
		restTemplate.postForLocation(PROGRAM_URL + "/search", null);
	}

	private void updateProgramIndex() {
		restTemplate.postForLocation(TWITTER_URL + "/search/", null);
	}
}
