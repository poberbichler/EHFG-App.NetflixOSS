package org.ehfg.app.program.data.output;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * basic representation of a session
 * 
 * @author patrick
 * @since 03.2014
 */
public class SessionDTO implements SessionRepresentation {
	private String id;
	private String name;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String code;

	private String location;
	private Set<String> speakers;

	public SessionDTO setId(String id) {
		this.id = id;
		return this;
	}

	public SessionDTO setName(String name) {
		this.name = name;
		return this;
	}

	public SessionDTO setDescription(String description) {
		this.description = description;
		return this;
	}

	public SessionDTO setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
		return this;
	}

	public SessionDTO setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
		return this;
	}

	public SessionDTO setCode(String code) {
		this.code = code;
		return this;
	}

	public SessionDTO setLocation(String location) {
		this.location = location;
		return this;
	}

	public SessionDTO setSpeakers(Set<String> speakers) {
		this.speakers = speakers;
		return this;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public LocalDateTime getStartTime() {
		return startTime;
	}

	@Override
	public LocalDateTime getEndTime() {
		return endTime;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public Set<String> getSpeakers() {
		return speakers;
	}
}
