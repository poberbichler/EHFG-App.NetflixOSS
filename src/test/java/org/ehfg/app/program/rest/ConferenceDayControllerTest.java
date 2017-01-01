package org.ehfg.app.program.rest;

import org.ehfg.app.program.days.ConferenceDay;
import org.ehfg.app.program.days.ConferenceDayService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author patrick
 * @since 11.2016
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ConferenceDayController.class)
public class ConferenceDayControllerTest {
	@MockBean
	private ConferenceDayService conferenceDayService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeClass
	public static void setup() {
		System.setProperty("eureka.client.enabled", "false");
	}

	@Test
	public void shouldReturnEmptyList() throws Exception {
		given(conferenceDayService.findDays()).willReturn(emptyList());

		mockMvc.perform(get("/conferencedays"))
				.andExpect(status().isOk())
				.andExpect(content().string("[]"));
	}

	@Test
	public void shouldReturnNotEmptyList() throws Exception {
		ConferenceDay conferenceDay = createDay();

		given(conferenceDayService.findDays()).willReturn(singleton(conferenceDay));

		mockMvc.perform(get("/conferencedays"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id").value(conferenceDay.getId()))
				.andExpect(jsonPath("$[0].description").value(conferenceDay.getDescription()))
				.andDo(print());
	}

	@Test
	public void replaceDays() throws Exception {
		ConferenceDay day = createDay();

		mockMvc.perform(post("/conferencedays")
				.content(getRequestBodyAsList(day))
				.contentType(MediaType.APPLICATION_JSON));

		verify(conferenceDayService).replaceDays(singletonList(day));
	}

	@Test
	public void addDay() throws Exception {
		ConferenceDay day = createDay();

		mockMvc.perform(put("/conferencedays")
				.content(getRequestBody(day))
				.contentType(MediaType.APPLICATION_JSON));

		verify(conferenceDayService).saveDay(day);
	}

	private ConferenceDay createDay() {
		ConferenceDay conferenceDay = new ConferenceDay();
		conferenceDay.setId(UUID.randomUUID().toString());
		conferenceDay.setDay(LocalDate.now());
		conferenceDay.setDescription("Day 1");
		return conferenceDay;
	}

	private String getRequestBody(ConferenceDay day) {
		return String.format("{\"id\": \"%s\", \"description\": \"%s\", \"date\": \"%s\" }", day.getId(), day.getDescription(), day.getDay());
	}

	private String getRequestBodyAsList(ConferenceDay... conferenceDays) {
		return Stream.of(conferenceDays)
				.map(this::getRequestBody)
				.collect(Collectors.joining(",", "[", "]"));
	}
}