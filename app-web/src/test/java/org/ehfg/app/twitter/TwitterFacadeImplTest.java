package org.ehfg.app.twitter;

import org.ehfg.app.base.AppConfig;
import org.ehfg.app.base.dto.MasterDataFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestOperations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterFacadeImplTest {
	@InjectMocks
	private TwitterFacadeImpl twitterFacade;

	@Mock
	private MasterDataFacade masterDataFacade;

	@Mock
	private RestOperations restTemplate;

	private static final String CURRENT_HASHTAG = "#EHFG2014";

	@Before
	public void initMocks() {
		AppConfig mockedConfig = mock(AppConfig.class);
		when(mockedConfig.getHashtag()).thenReturn(CURRENT_HASHTAG);

		when(masterDataFacade.getAppConfiguration()).thenReturn(mockedConfig);
	}

	@Test
	public void shouldDoNothingIfCurrentStreamIsRunning() {
		when(restTemplate.getForObject(TwitterFacadeImpl.TWITTER_URL + "/listener", List.class)).thenReturn(Collections.singletonList(CURRENT_HASHTAG));

		TwitterStreamStatus status = twitterFacade.checkIfRelevantStreamIsRunning();
		assertEquals(TwitterStreamStatus.RUNNING, status);

		verify(restTemplate).getForObject(TwitterFacadeImpl.TWITTER_URL + "/listener", List.class);
		verifyNoMoreInteractions(restTemplate);
	}

	@Test
	public void shouldRestartStreamIfNoOneIsActive() {
		when(restTemplate.getForObject(TwitterFacadeImpl.TWITTER_URL + "/listener", List.class)).thenReturn(Collections.<String> emptyList());

		callAndVerifyRestartBehaviour();
	}

	@Test
	public void shouldRestartStreamIfWrongStreamWasFound() {
		when(restTemplate.getForObject(TwitterFacadeImpl.TWITTER_URL + "/listener", List.class)).thenReturn(
				Arrays.asList("asdfasdf", "", "umpalumpa", CURRENT_HASHTAG.concat("a"), CURRENT_HASHTAG.substring(1)));
		
		callAndVerifyRestartBehaviour();
	}
	
	/**
	 * calls the tested class, and verifies the behaviour in case the hashtag was not found
	 */
	private void callAndVerifyRestartBehaviour() {
		TwitterStreamStatus status = twitterFacade.checkIfRelevantStreamIsRunning();
		assertEquals(TwitterStreamStatus.HAD_TO_RESTART, status);

		verify(restTemplate).getForObject(TwitterFacadeImpl.TWITTER_URL + "/listener", List.class);
		verify(restTemplate).postForObject(TwitterFacadeImpl.TWITTER_URL + "/listener/{hashtag}", null, List.class, CURRENT_HASHTAG);
	}
}
