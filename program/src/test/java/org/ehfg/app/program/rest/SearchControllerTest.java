package org.ehfg.app.program.rest;

import org.ehfg.app.program.search.ResultType;
import org.ehfg.app.program.search.SearchResultItem;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
	public void getSearchResultWithDefaultValue() throws Exception {
		String searchInput = "loremipsum";

		MultiValueMap<ResultType, SearchResultItem> resultMap = createResultMap();
		given(searchService.search(searchInput, SearchController.DEFAULT_MAX_SEARCH_RESULTS)).willReturn(resultMap);

		mockMvc.perform(get("/search/{input}/", searchInput).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

		verify(searchService).search(searchInput, SearchController.DEFAULT_MAX_SEARCH_RESULTS);
	}

	private MultiValueMap<ResultType, SearchResultItem> createResultMap() {
		MultiValueMap<ResultType, SearchResultItem> resultMap = new LinkedMultiValueMap<>();
		resultMap.add(ResultType.SESSIONS, new SearchResultItem("id", ResultType.SESSIONS, "name"));
		return resultMap;
	}

	@Test
	public void overrideSearchResultAmount() throws Exception {
		String searchInput = "input";
		int maxResults = SearchController.DEFAULT_MAX_SEARCH_RESULTS + 1;

		given(searchService.search(searchInput, maxResults)).willReturn(createResultMap());

		mockMvc.perform(get("/search/{input}/{maxResults}", searchInput, maxResults).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

		verify(searchService).search(searchInput, maxResults);

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