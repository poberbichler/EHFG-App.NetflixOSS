package org.ehfg.app.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 06.2016
 */
public class SearchResultItem implements SearchResultItemRepresentation {
	private final String id;
	private final ResultType resultType;
	private final String description;

	@JsonCreator
	public SearchResultItem(@JsonProperty("id") String id,
							@JsonProperty("type") ResultType resultType,
							@JsonProperty("description") String description) {
		this.id = id;
		this.resultType = resultType;
		this.description = description;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public ResultType getType() {
		return resultType;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
