package org.ehfg.app.program.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 11.2016
 */
@Service
class SearchServiceImpl implements SearchService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public SearchResult search(String input) {
		logger.info("searching for [{}]", input);
		return new SearchResult(null);
	}

	@Override
	public boolean updateIndex() {
		logger.info("updating index...");
		return true;
	}
}
