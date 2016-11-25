package org.ehfg.app.program.rest;

import org.ehfg.app.program.search.SearchResult;
import org.ehfg.app.program.search.SearchService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author patrick
 * @since 11.2016
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
public class SearchControllerTest {
	@MockBean
	private SearchService searchService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeClass
	public static void setup() {
		System.setProperty("eureka.client.enabled", "false");
	}

	@Test
	public void getSearchResult() throws Exception {
		final String searchInput = "loremipsum";

		given(searchService.search(searchInput)).willReturn(new SearchResult("ipsum"));

		mockMvc.perform(MockMvcRequestBuilders.get("/search/{input}", searchInput))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());

		verify(searchService).search(searchInput);
	}

	@Test
	public void updateIndexError() throws Exception {
		given(searchService.updateIndex()).willReturn(false);

		mockMvc.perform(post("/search")).andExpect(status().isInternalServerError());

		verify(searchService).updateIndex();
	}

	@Test
	public void updateIndexSuccess() throws Exception {
		given(searchService.updateIndex()).willReturn(true);

		mockMvc.perform(post("/search")).andExpect(status().isOk());

		verify(searchService).updateIndex();
	}

}