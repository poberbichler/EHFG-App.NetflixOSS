package org.ehfg.app.program.search;

/**
 * @author patrick
 * @since 11.2016
 */
public class SearchResultItem {
	private final String id;
	private final ResultType type;
	private final String name;

	public SearchResultItem(String id, ResultType type, String name) {
		this.id = id;
		this.type = type;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public ResultType getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}
