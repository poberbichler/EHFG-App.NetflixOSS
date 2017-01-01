package org.ehfg.app.program;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.Collection;
import java.util.List;
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
	public List<ConferenceDayDTO> findDays() {
		return restTemplate.getForObject("http://EHFGAPP-PROGRAM/conferencedays", List.class);
	}

	@Override
	public void saveDays(List<ConferenceDayDTO> dayList) {
		restTemplate.postForObject("http://EHFGAPP-PROGRAM/conferencedays", dayList, Collection.class);
	}

	@Override
	public Collection<SessionDTO> findAllSessionsWithoutDayInformation() {
		ParameterizedTypeReference<Collection<SessionDTO>> responseType = new ParameterizedTypeReference<Collection<SessionDTO>>() {
		};

		ResponseEntity<Collection<SessionDTO>> exchange = restTemplate.exchange("http://EHFGAPP-PROGRAM/sessions", HttpMethod.GET, null, responseType);

		Collection<SessionDTO> responseBody = exchange.getBody();
		return responseBody;
	}

	@Override
	public List<String> findAvailableLocations() {
		return findAllSessionsWithoutDayInformation().stream()
				.map(SessionDTO::getLocation)
				.distinct()
				.collect(Collectors.toList());
	}
}
