package org.ehfg.app.program.search;

import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author patrick
 * @since 11.2016
 */
public class SearchResult {
	@XmlElementWrapper(name = "sessions")
	private final Collection<SearchResultItem> sessions = new LinkedList<>();
	@XmlElementWrapper(name = "speakers")
	private final Collection<SearchResultItem> speakers = new LinkedList<>();

	public SearchResult addItem(SearchResultItem item) {
		if (item.getType() == ResultType.SPEAKER) {
			speakers.add(item);
		} else {
			sessions.add(item);
		}

		return this;
	}

	public Collection<SearchResultItem> getSpeakers() {
		return speakers;
	}

	public Collection<SearchResultItem> getSessions() {
		return sessions;
	}
}
