package org.ehfg.app.program.search;

/**
 * @author patrick
 * @since 11.2016
 */
public class SearchResultItem {
	private final String id;
	private final ResultType type;
	private final String description;

	public SearchResultItem(String id, ResultType type, String description) {
		this.id = id;
		this.type = type;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public ResultType getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}
}
