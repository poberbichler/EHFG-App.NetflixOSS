package org.ehfg.app.program.service;

import org.ehfg.app.program.data.db.SpeakerRepository;
import org.ehfg.app.program.data.db.SessionRepository;
import org.ehfg.app.program.data.output.ConferenceDayRepresentation;
import org.ehfg.app.program.data.output.ConferenceDayWithSessionsDTO;
import org.ehfg.app.program.data.output.SessionRepresentation;
import org.ehfg.app.program.data.output.SpeakerRepresentation;
import org.ehfg.app.program.days.ConferenceDay;
import org.ehfg.app.program.days.ConferenceDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toMap;

/**
 * @author patrick
 * @since 11.2016
 */
@Service
class ProgramServiceImpl implements ProgramService {
	private final ConferenceDayService conferenceDayService;

	private final SessionRepository sessionRepository;
	private final SpeakerRepository speakerRepository;

	@Autowired
	public ProgramServiceImpl(ConferenceDayService conferenceDayService, SessionRepository sessionRepository, SpeakerRepository speakerRepository) {
		this.conferenceDayService = conferenceDayService;
		this.sessionRepository = sessionRepository;
		this.speakerRepository = speakerRepository;
	}

	@Override
	public Collection<? extends SessionRepresentation> findSessions() {
		return sessionRepository.findAll();
	}

	@Override
	public Collection<? extends SpeakerRepresentation> findSpeakers() {
		return speakerRepository.findAll();
	}

	@Override
	public Map<LocalDate, ? extends ConferenceDayRepresentation> findProgram() {
		Collection<ConferenceDay> conferenceDays = conferenceDayService.findDays();
		Predicate<SessionRepresentation> sessionWithValidConferenceDay = (session -> findDay(conferenceDays, session).isPresent());
		Function<SessionRepresentation, LocalDate> localDateOfSession = (session -> session.getStartTime().toLocalDate());

		return sessionRepository.findAll().stream()
				.filter(sessionWithValidConferenceDay)
				.collect(toMap(localDateOfSession,
						session -> new ConferenceDayWithSessionsDTO(findDay(conferenceDays, session).get().getDescription(), session),
						(existingDay, newSession) -> existingDay.addSession(newSession.getSessions()),
						TreeMap::new));
	}

	private Optional<ConferenceDay> findDay(Collection<ConferenceDay> days, SessionRepresentation session) {
		LocalDateTime sessionDate = session.getStartTime();

		return days.stream()
				.filter(day -> day.getDay().equals(sessionDate.toLocalDate()))
				.findFirst();
	}
}
