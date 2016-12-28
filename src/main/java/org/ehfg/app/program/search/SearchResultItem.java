package org.ehfg.app.program.search;

import com.google.common.base.MoreObjects;

import java.util.Objects;

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

	@Override
	public boolean equals(Object thatObject) {
		if (this == thatObject) {
			return true;
		}

		if (!(thatObject instanceof SearchResultItem)) {
			return false;
		}

		SearchResultItem that = (SearchResultItem) thatObject;
		return Objects.equals(this.id, that.id)
				&& Objects.equals(this.type, that.type)
				&& Objects.equals(this.description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type, description);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("type", type)
				.add("description", description)
				.toString();
	}
}
