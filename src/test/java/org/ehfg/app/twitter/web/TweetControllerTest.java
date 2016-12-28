package org.ehfg.app.twitter.web;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.ehfg.app.twitter.data.TweetPage;
import org.ehfg.app.twitter.service.TwitterService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author poberbichler
 * @since 12.2016
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TweetController.class)
public class TweetControllerTest {
	private static final String HASHTAG = "#EHFG2016";

	@MockBean
	private TwitterService twitterService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeClass
	public static void setup() {
		System.setProperty("eureka.client.enabled", "false");
	}

	@Test
	public void printResultEventWhenTweetsAreEmpty() throws Exception {
		given(twitterService.findPage(anyInt(), anyInt())).willReturn(new TweetPage(new PageImpl<>(emptyList()), HASHTAG));

		mockMvc.perform(get("/tweets/page/{pageIndex}", 0))
				.andExpect(status().isOk())
				.andExpect(content().string(allOf(
						containsString("currentHashtag"),
						containsString("totalPage"),
						containsString("last"),
						containsString(HASHTAG))))
				.andDo(print());

		verify(twitterService).findPage(anyInt(), anyInt());
	}
}