package org.ehfg.app.twitter.search;

/**
 * @author patrick
 * @since 11.2016
 */
public interface SearchService {
	SearchResult find(String input, int maxResults);

	boolean refreshIndex();
}
