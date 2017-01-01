package org.ehfg.app.program;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * basic representation of a session
 *
 * @author patrick
 * @since 03.2014
 */
public class SessionDTO {
	private final String id;
	private final String name;
	private final String description;
	private final String code;
	private final String location;

	@JsonDeserialize(using = NumberTimestampLocalDateTimeDeserializer.class)
	private final LocalDateTime startTime;
	@JsonDeserialize(using = NumberTimestampLocalDateTimeDeserializer.class)
	private final LocalDateTime endTime;

	private final Set<String> speakers;

	@JsonCreator
	public SessionDTO(@JsonProperty("id") String id,
					  @JsonProperty("name") String name,
					  @JsonProperty("description") String description,
					  @JsonProperty("code") String code,
					  @JsonProperty("location") String location,
					  @JsonProperty("startTime") LocalDateTime startTime,
					  @JsonProperty("endTime") LocalDateTime endTime,
					  @JsonProperty("speakers") Set<String> speakers) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.code = code;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.speakers = speakers;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getCode() {
		return code;
	}

	public String getLocation() {
		return location;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public Set<String> getSpeakers() {
		return speakers;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("code", code)
				.add("startTime", startTime)
				.add("endTime", endTime)
				.add("location", location)
				.toString();
	}
}
