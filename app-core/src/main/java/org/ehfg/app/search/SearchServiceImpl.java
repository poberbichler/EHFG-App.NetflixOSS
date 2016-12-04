package org.ehfg.app.search;

import org.ehfg.app.rest.SearchResultRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author patrick
 * @since 06.2016
 */
@Service
public class SearchServiceImpl implements SearchService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final AsyncRestTemplate asyncRestTemplate;
	private final org.springframework.cloud.client.discovery.DiscoveryClient discoveryClient;

	@Autowired
	public SearchServiceImpl(AsyncRestTemplate asyncRestTemplate, org.springframework.cloud.client.discovery.DiscoveryClient discoveryClient) {
		this.asyncRestTemplate = asyncRestTemplate;
		this.discoveryClient = discoveryClient;
	}

	@Override
	public SearchResultRepresentation findBy(String input) {
		try {
			logger.info("searching for [{}], calling the endpoint", input);

			ListenableFuture<ResponseEntity<SearchResult>> twitterFuture = asyncRestTemplate.getForEntity("http://localhost:8082/search/{input}", SearchResult.class, input);
			ListenableFuture<ResponseEntity<Map<ResultType, List<SearchResultItem>>>> programFuture = asyncRestTemplate.exchange("http://localhost:8083/search/{input}", HttpMethod.GET, null,
					new ParameterizedTypeReference<Map<ResultType, List<SearchResultItem>>>() {}, input);

			ResponseEntity<SearchResult> twitterResultEntity = twitterFuture.get(5L, TimeUnit.SECONDS);
			ResponseEntity<Map<ResultType, List<SearchResultItem>>> programResultEntity = programFuture.get(5L, TimeUnit.SECONDS);

			List<SearchResult.SearchResultData> data = new ArrayList<>();
			for (Map.Entry<ResultType, List<SearchResultItem>> entry : programResultEntity.getBody().entrySet()) {
				data.add(new SearchResult.SearchResultData(entry.getKey(), entry.getValue()));
			}
			return new SearchResult(twitterResultEntity.getBody().getTweets(), data);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			logger.error("an exception occured when searching for [{}]", input, e);
			return SearchResult.empty();
		}
	}

	@Override
	public void refreshIndex() {
		logger.info("updating indexes...");

		asyncRestTemplate.postForEntity("http://localhost:8082/search/", null, null);
		asyncRestTemplate.postForEntity("http://localhost:8083/search/", null, null);
	}

	public static class SearchResponse {
		private Map<ResultType, List<SearchResultItem>> data;

		public Map<ResultType, List<SearchResultItem>> getData() {
			return data;
		}

		public void setData(Map<ResultType, List<SearchResultItem>> data) {
			this.data = data;
		}
	}
}
