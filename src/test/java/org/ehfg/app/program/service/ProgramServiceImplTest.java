package org.ehfg.app.program.service;

import org.assertj.core.api.Assertions;
import org.ehfg.app.program.data.db.SessionRepository;
import org.ehfg.app.program.data.db.SpeakerRepository;
import org.ehfg.app.program.data.output.ConferenceDayRepresentation;
import org.ehfg.app.program.data.output.SessionDTO;
import org.ehfg.app.program.days.ConferenceDay;
import org.ehfg.app.program.days.ConferenceDayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author poberbichler
 * @since 12.2016
 */
@RunWith(MockitoJUnitRunner.class)
public class ProgramServiceImplTest {
	@InjectMocks
	private ProgramServiceImpl programService;

	@Mock
	private SessionRepository sessionRepository;

	@Mock
	private ConferenceDayService conferenceDayService;

	@Test
	public void onlyReturnSessionWithValidConferenceDay() throws Exception {
		given(conferenceDayService.findDays()).willReturn(createDayList(3));

		SessionDTO secondDay2Session = new SessionDTO().setStartTime(now().plusDays(2)).setName("Session on Day 2").setCode("C2");
		SessionDTO firstDay2Session = new SessionDTO().setStartTime(now().plusDays(2).minusMinutes(5)).setName("Second Session on Day 2").setCode("S2");
		given(sessionRepository.findAll()).willReturn(Arrays.asList(
				new SessionDTO().setStartTime(now().minusDays(1)).setDescription("invalid session"),
				new SessionDTO().setStartTime(now()).setName("Session on Day 1").setCode("C1"),
				secondDay2Session,
				firstDay2Session,
				new SessionDTO().setStartTime(now().plusDays(1)).setName("Session on Day 3").setCode("C3"),
				new SessionDTO().setStartTime(now().plusDays(3)).setName("another invalid session")
		));


		Map<LocalDate, ? extends ConferenceDayRepresentation> program = programService.findProgram();
		assertThat(program).hasSize(3);

		ConferenceDayRepresentation secondDay = program.get(LocalDate.now().plusDays(2));
		assertThat(secondDay.getSessions()).hasSize(2);
		assertThat(secondDay.getSessions()).containsExactly(firstDay2Session, secondDay2Session); // entries should be ordered by starttime
	}

	private List<ConferenceDay> createDayList(int days) {
		List<ConferenceDay> result = new ArrayList<>(days);

		for (int i = 0; i < days; i++) {
			ConferenceDay day = new ConferenceDay();
			day.setDate(LocalDate.now().plusDays(i));
			day.setDescription("Day " + i);
			day.setId(Integer.toString(i));
			result.add(day);
		}

		return result;
	}
}