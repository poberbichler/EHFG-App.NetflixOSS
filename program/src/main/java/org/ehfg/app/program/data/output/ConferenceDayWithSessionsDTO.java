package org.ehfg.app.program.data.output;

import com.google.common.base.MoreObjects;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Representation <b>only</b> of the necessary information of a conferenceday, including sessions.<br>
 * Should primarily used for the rest endpoint
 *
 * @author patrick
 * @since 05.2015
 */
public class ConferenceDayWithSessionsDTO implements ConferenceDayRepresentation {
	private final String description;
	private final Collection<SessionRepresentation> sessions = new TreeSet<>();

	public ConferenceDayWithSessionsDTO(String description, SessionRepresentation initialSession) {
		this.description = description;
		this.sessions.add(initialSession);
	}

	/**
	 * adds the given session to the internal sessionlist
	 * @return the current ConferenceDayWithSessionsDTO
	 */
	public ConferenceDayWithSessionsDTO addSession(SessionRepresentation session) {
		sessions.add(session);
		return this;
	}

	/**
	 * adds the given collection of sessions to the internal sessionList
	 * @return the current ConferenceDayWithSessionsDTO
	 */
	public ConferenceDayWithSessionsDTO addSession(Collection<SessionRepresentation> session) {
		this.sessions.addAll(session);
		return this;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Collection<SessionRepresentation> getSessions() {
		return Collections.unmodifiableCollection(sessions);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("description", description)
				.add("sessions", sessions)
				.toString();
	}
}
