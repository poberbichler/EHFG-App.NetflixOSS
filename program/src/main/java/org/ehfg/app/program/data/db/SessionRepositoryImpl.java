package org.ehfg.app.program.data.db;

import org.ehfg.app.program.data.input.events.Event;
import org.ehfg.app.program.data.input.events.RssEvent;
import org.ehfg.app.program.data.input.speakerevents.RssSpeakerEvents;
import org.ehfg.app.program.data.input.speakerevents.SpeakerEvent;
import org.ehfg.app.program.data.output.SessionDTO;
import org.ehfg.app.program.data.retrieval.AbstractDataRetrievalStrategy;
import org.ehfg.app.program.service.SessionCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.*;

/**
 * @author patrick
 * @since 01.2015
 */
@Repository
class SessionRepositoryImpl implements SessionRepository {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final AbstractDataRetrievalStrategy<RssEvent> eventStrategy;
	private final AbstractDataRetrievalStrategy<RssSpeakerEvents> speakerEventStrategy;

	private Map<String, Set<String>> speakerMap;

	@Autowired
	public SessionRepositoryImpl(AbstractDataRetrievalStrategy<RssEvent> eventStrategy, AbstractDataRetrievalStrategy<RssSpeakerEvents> speakerEventStrategy) {
		this.eventStrategy = eventStrategy;
		this.speakerEventStrategy = speakerEventStrategy;
	}

	@Override
	@SessionCache
	public Collection<SessionDTO> findAll() {
		updateSpeakerMap();
		List<Event> sessions = eventStrategy.fetchData().getChannel().getItems();

		logger.info("received {} sessions", sessions.size());

		return sessions.stream()
				.map(this::toSessionDTO)
				.sorted()
				.collect(toList());
	}

	private SessionDTO toSessionDTO(Event event) {
		logger.trace("preparing text for session {}", event);

		String details = EscapeUtils.escapeText(event.getDetails());

		// the backend does return '?' instead of the real character... *sigh*
		String eventName = event.getEvent()
				.replace("?Best", "\"Best")
				.replace("Service?", "Service\"")
				.replace("?Healthy?", "\"Healthy\"")
				.replace("Alzheimer?s", "Alzheimer's");

		return new SessionDTO()
				.setDescription(details)
				.setEndTime(event.getDay().atTime(event.getEnd()))
				.setId(event.getId())
				.setLocation(event.getRoom())
				.setName(eventName)
				.setCode(event.getCode())
				.setStartTime(event.getDay().atTime(event.getStart()))
				.setSpeakers(speakerMap.getOrDefault(event.getId(), emptySet()));

	}

	private void updateSpeakerMap() {
		List<SpeakerEvent> speakerEvents = speakerEventStrategy.fetchData().getChannel().getSpeakerEvents();
		logger.info("received {} speakers for events", speakerEvents.size());
		this.speakerMap = speakerEvents.stream()
				.collect(groupingBy(SpeakerEvent::getEventid,
						mapping(SpeakerEvent::getSpeakerid, toSet())));
	}
}
