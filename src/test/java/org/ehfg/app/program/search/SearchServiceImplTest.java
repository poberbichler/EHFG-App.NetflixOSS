package org.ehfg.app.program.search;

import org.assertj.core.api.Assertions;
import org.ehfg.app.program.days.ConferenceDayService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author poberbichler
 * @since 12.2016
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, properties = {"program.from.classpath=true"})
public class SearchServiceImplTest {

	@Autowired
	private SearchService searchService;

	@MockBean
	private ConferenceDayService conferenceDayService;

	@Before
	public void updateIndex() {
		searchService.updateIndex();
	}

	@Test
	public void returnRightValuesWhenSearchingForHelmut() {
		Map<ResultType, List<SearchResultItem>> result = searchService.search("helmut", 5);

		assertThat(result).hasSize(2);

		List<SearchResultItem> sessionResult = result.get(ResultType.SESSIONS);
		assertThat(sessionResult).hasSize(3);
		assertThat(sessionResult).contains(
				new SearchResultItem("76", ResultType.SESSIONS, "Opening Plenary"),
				new SearchResultItem("81", ResultType.SESSIONS, "Public health leadership"),
				new SearchResultItem("105", ResultType.SESSIONS, "Closing Plenary"));

		List<SearchResultItem> speakerResult = result.get(ResultType.SPEAKER);
		assertThat(speakerResult).hasSize(1);
		assertThat(speakerResult).contains(new SearchResultItem("124", ResultType.SPEAKER, "Helmut Brand"));
	}

	@Test
	public void returnEmptyResult() {
		Map<ResultType, List<SearchResultItem>> result = searchService.search("lorem ipsum", 5);

		assertThat(result).hasSize(2);
		assertThat(result.get(ResultType.SESSIONS)).isEmpty();
		assertThat(result.get(ResultType.SPEAKER)).isEmpty();
	}

	@Test
	public void maxResultShouldLimitResults() {
		Map<ResultType, List<SearchResultItem>> result = searchService.search("helmut", 1);

		assertThat(result).hasSize(2);
		List<SearchResultItem> flatResult = result.values().stream().flatMap(Collection::stream).collect(toList());
		assertThat(flatResult).hasSize(1);
	}
}