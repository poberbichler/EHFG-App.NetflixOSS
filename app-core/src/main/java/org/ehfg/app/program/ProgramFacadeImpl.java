package org.ehfg.app.program;

import org.ehfg.app.rest.ConferenceDayRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author patrick
 * @since 06.04.2014
 */
@Component
class ProgramFacadeImpl implements ProgramFacade {
	private final RestOperations restTemplate;

	@Autowired
	public ProgramFacadeImpl(RestOperations restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Collection<SpeakerDTO> findAllSpeakers() {
		return restTemplate.getForObject("http://EHFGAPP-PROGRAM/speakers", Collection.class);
	}

	@Override
	public Collection<SpeakerDTO> findSpeakersWithSession() {
		final Set<String> eventSpeakers = findEverySpeakerForSession();
		return findAllSpeakers().stream()
				.filter(speaker -> eventSpeakers.contains(speaker.getId()))
				.distinct().sorted()
				.collect(Collectors.toList());
	}

	private Set<String> findEverySpeakerForSession() {
		return findAllSessionsWithoutDayInformation().stream()
				.flatMap(session -> session.getSpeakers().stream())
				.collect(Collectors.toSet());
	}

	@Override
	public Map<LocalDate, ? extends ConferenceDayRepresentation> findAllSessions() {
		return restTemplate.getForObject("http://EHFGAPP-PROGRAM/program", Map.class);
	}

	@Override
	public List<ConferenceDayDTO> findDays() {
		return restTemplate.getForObject("http://EHFGAPP-PROGRAM/conferencedays", List.class);
	}

	@Override
	public void saveDays(List<ConferenceDayDTO> dayList) {
		restTemplate.postForObject("http://EHFGAPP-PROGRAM/conferencedays", dayList, Collection.class);
	}

	@Override
	public Collection<SessionDTO> findAllSessionsWithoutDayInformation() {
		return restTemplate.getForObject("http://EHFGAPP-PROGRAM/sessions", Collection.class);
	}

	@Override
	public List<String> findAvailableLocations() {
		return findAllSessionsWithoutDayInformation().stream()
				.map(SessionDTO::getLocation)
				.distinct()
				.collect(Collectors.toList());
	}
}
