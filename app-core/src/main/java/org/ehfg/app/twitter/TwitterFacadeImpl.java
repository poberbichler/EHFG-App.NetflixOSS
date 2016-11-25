package org.ehfg.app.twitter;

import org.apache.commons.lang3.Validate;
import org.ehfg.app.base.ConfigurationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author patrick
 * @since 03.2014
 */
@Component
class TwitterFacadeImpl implements TwitterFacade {
	static final String TWITTER_URL = "http://EHFGAPP-TWITTER";

	private final RestOperations restTemplate;
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public TwitterFacadeImpl(RestOperations restTemplate, MasterDataFacade masterDataFacade) {
		this.restTemplate = restTemplate;
		this.masterDataFacade = masterDataFacade;
	}

	@Override
	public Collection<String> findStreams() {
		return restTemplate.getForObject(TWITTER_URL + "/listener", Collection.class);
	}

	@Override
	public void addStream(String hashtag) {
		Validate.notNull(hashtag, "hashtag must not be null");
		restTemplate.postForObject(TWITTER_URL + "/listener/{hashtag}", null, List.class, hashtag);
	}

	@Override
	public void removeStream(String hashtag) {
		Validate.notNull(hashtag, "hashtag must not be null");
		restTemplate.delete(TWITTER_URL + "/listener/{hashtag}", hashtag);
	}

	@Override
	public String findHashtag() {
		return masterDataFacade.getAppConfiguration().getHashtag();
	}

	@Override
	public Collection<TweetDTO> findNewerTweetsForCongress(LocalDateTime lastTweet) {
		final ConfigurationDTO config = masterDataFacade.getAppConfiguration();
		if (config != null && config.getHashtag() != null) {
			return restTemplate.getForObject(TWITTER_URL + "/{hashtag}/{timestamp}", Collection.class, config.getHashtag(), lastTweet);
		}

		return Collections.emptyList();
	}

	@Override
	public Object findTweetPage(int pageId) {
		return findTweetPageWithSize(pageId, masterDataFacade.getAppConfiguration().getNumberOfTweets());
	}


	@Override
	public Object findTweetPageWithSize(int pageId, int pageSize) {
		Validate.notNull(pageId, "pageId must not be null!");
		Validate.notNull(pageSize, "pageSize must not be null!");

		String currentHashtag = findHashtag().substring(1);

		return restTemplate.getForObject(TWITTER_URL + "/tweets/{hashtag}/page/{pageCounter}/{size}", Object.class, currentHashtag, pageId, pageSize);
	}


	@Override
	public TwitterStreamStatus checkIfRelevantStreamIsRunning() {
		final Collection<String> currentStreams = findStreams();
		final String thisYearsHashtag = findHashtag();

		if (currentStreams.isEmpty() || !currentStreams.contains(thisYearsHashtag)) {
			addStream(thisYearsHashtag);
			return TwitterStreamStatus.HAD_TO_RESTART;
		}

		return TwitterStreamStatus.RUNNING;
	}

	@Override
	public Collection<TwitterStatisticLine> findStats() {
		throw new IllegalStateException("TwitterFacadeImpl.findStats not supported yet");
	}

	@Override
	public List<TweetDTO> findTweetsForExport(String hashtag) {
		throw new IllegalStateException("TwitterFacadeImpl.findTweetsForExport not supported yet");
	}
}
