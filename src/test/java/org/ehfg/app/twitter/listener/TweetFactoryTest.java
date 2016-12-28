package org.ehfg.app.twitter.listener;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.ehfg.app.twitter.data.Tweet;
import org.ehfg.app.twitter.data.TwitterUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.twitter.api.Entities;
import org.springframework.social.twitter.api.UrlEntity;

@RunWith(MockitoJUnitRunner.class)
public final class TweetFactoryTest {
	@Mock
	private org.springframework.social.twitter.api.Tweet status;

	@Mock
	private TwitterUser user;

	private static final String ANY_HASHTAG = "#EHFG2014";
	private static final long ANY_ID = 1235234908L;
	private static final String MESSAGE = "OMG, #EHFG2014 was awsome... #EHFG2013; http://t.co/IyExg1bi61 app.ehfg.org/1234 app.ehfg.org/12";

	@Before
	public void initMocks() {
		UrlEntity urlEntity = new UrlEntity("app.ehfg.org/123", "https://app.ehfg.org/123", "http://t.co/IyExg1bi61", null);
		Entities entities = new Entities(
				Arrays.asList(urlEntity),
				Collections.emptyList(),
				Collections.emptyList(),
				Collections.emptyList());

		when(status.getEntities()).thenReturn(entities);
		when(status.getCreatedAt()).thenReturn(new Date());
		when(status.getId()).thenReturn(ANY_ID);
		when(status.getText()).thenReturn(MESSAGE);
		when(status.getUnmodifiedText()).thenReturn(MESSAGE);
	}

	@Test
	public void updateUrlsCorrect() {
		Tweet createdTweet = TweetFactory.create(status, ANY_HASHTAG, user);
		assertThat(user, is(createdTweet.getAuthor()));
		assertThat(createdTweet.getMessage(), is(MESSAGE));
		assertThat(createdTweet.getHashtag(), is(ANY_HASHTAG));

		final String expectedFormattedMessage =
				"OMG, <span class=\"hashtag\">#EHFG2014</span> was awsome... <span class=\"hashtag\">#EHFG2013;</span> " +
						"<a href=\"#\" onclick=\"window.open('https://app.ehfg.org/123', '_blank')\">" +
						"app.ehfg.org/123</a> app.ehfg.org/1234 app.ehfg.org/12 ";

		assertThat(createdTweet.getFormattedMesssage(), is(expectedFormattedMessage));
	}
}
