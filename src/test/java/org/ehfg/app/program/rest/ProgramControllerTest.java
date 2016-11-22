package org.ehfg.app.program.rest;

import org.ehfg.app.program.service.ProgramService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author patrick
 * @since 11.2016
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProgramController.class)
public class ProgramControllerTest {
	@MockBean
	private ProgramService programService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeClass
	public static void setup() {
		System.setProperty("eureka.client.enabled", "false");
	}

	@Test
	public void shouldReturnSessions() throws Exception {
		given(programService.findSessions()).willReturn(emptyList());

		mockMvc.perform(get("/sessions"))
				.andExpect(status().isOk())
				.andExpect(content().string("[]"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());
	}

	@Test
	public void shouldReturnSpeakers() throws Exception {
		given(programService.findSpeakers()).willReturn(emptyList());

		mockMvc.perform(get("/speakers"))
				.andExpect(status().isOk())
				.andExpect(content().string("[]"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());
	}

	@Test
	public void shouldReturnProgram() throws Exception {
		given(programService.findProgram()).willReturn(emptyMap());

		mockMvc.perform(get("/program"))
				.andExpect(status().isOk())
				.andExpect(content().string("{}"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());
	}
}