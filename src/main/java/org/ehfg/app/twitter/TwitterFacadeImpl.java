package org.ehfg.app.twitter;

import org.apache.commons.lang3.Validate;
import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.Collection;
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
	public List<TweetDTO> findTweetsForExport(String hashtag) {
		throw new IllegalStateException("TwitterFacadeImpl.findTweetsForExport not supported yet");
	}
}
