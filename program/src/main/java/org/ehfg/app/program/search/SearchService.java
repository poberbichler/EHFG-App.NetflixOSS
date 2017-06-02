package org.ehfg.app.program.search;

import java.util.List;
import java.util.Map;

/**
 * @author patrick
 * @since 11.2016
 */
public interface SearchService {
	Map<ResultType, List<SearchResultItem>> search(String input, int maxResults);
	boolean updateIndex();
}
