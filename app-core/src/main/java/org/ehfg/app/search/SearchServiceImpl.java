package org.ehfg.app.search;

import org.ehfg.app.rest.SearchResultRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

/**
 * @author patrick
 * @since 06.2016
 */
@Service
public class SearchServiceImpl implements SearchService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final String baseUrl;

	@Autowired
	public SearchServiceImpl(@Value("${search.endpoint:http://localhost:8081}") String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public SearchResultRepresentation findBy(String input) {
		logger.info("searching for [{}], calling the endpoint", input, baseUrl);

		try {
			return new RestTemplate().getForObject(baseUrl + "/rest/search/{input}", SearchResult.class, input);
		} catch (Exception e) {
			logger.error("an exception occured when searching for [{}]", input, e);
			return SearchResult.empty();
		}
	}
}
