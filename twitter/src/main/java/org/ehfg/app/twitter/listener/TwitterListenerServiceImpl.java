package org.ehfg.app.twitter.listener;

import com.google.common.base.Preconditions;
import org.ehfg.app.twitter.data.Hashtag;
import org.ehfg.app.twitter.service.TweetRepository;
import org.ehfg.app.twitter.service.TwitterUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/**
 * @author patrick
 * @since 10.2016
 */
@Service
public class TwitterListenerServiceImpl implements TwitterListenerService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final TweetRepository tweetRepository;
	private final TwitterUserRepository twitterUserRepository;

	private final Twitter twitterTemplate;

	private final Map<Hashtag, Stream> streamMap = new HashMap<>();

	@Autowired
	public TwitterListenerServiceImpl(TweetRepository tweetRepository, TwitterUserRepository twitterUserRepository, Twitter twitterTemplate) {
		this.tweetRepository = tweetRepository;
		this.twitterUserRepository = twitterUserRepository;
		this.twitterTemplate = twitterTemplate;
	}

	@PreDestroy
	public void cleanListeners() {
		logger.info("cleaning up the rest of the listeners");
		streamMap.keySet().stream().forEach(this::removeListener);
	}

	@Override
	public Collection<String> findAll() {
		return streamMap.keySet().stream()
				.map(Hashtag::getHashtagWithHash)
				.collect(toList());
	}

	@Override
	public Collection<String> addListener(Hashtag hashtag) {
		Preconditions.checkNotNull(hashtag, "hashtag must not be null");

		logger.info("adding listener for hashtag [{}]", hashtag);
		final Stream filter = twitterTemplate.streamingOperations().filter(hashtag.getHashtagWithoutHash(),
						singletonList(new PersistingStreamListener(hashtag, tweetRepository, twitterUserRepository)));

		streamMap.put(hashtag, filter);
		return findAll();
	}

	@Override
	public Collection<String> removeListener(Hashtag hashtag) {
		Preconditions.checkNotNull(hashtag, "hashtag must not be null");

		logger.info("removing listener for hashtag [{}]", hashtag);

		Stream listener = streamMap.get(hashtag);
		if (listener == null) {
			logger.warn("no listener for hashtag [{}] found", hashtag);
			return findAll();
		}

		listener.close();
		streamMap.remove(hashtag);
		return findAll();
	}
}
