package org.ehfg.app.program.search;

/**
 * @author patrick
 * @since 11.2016
 */
public interface SearchService {
	SearchResult search(String input);
	boolean updateIndex();
}
