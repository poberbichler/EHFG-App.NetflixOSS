package org.ehfg.app.twitter.service;

import org.ehfg.app.twitter.data.Hashtag;
import org.ehfg.app.twitter.data.TweetRepresentation;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author patrick
 * @since 09.2016
 */
public interface TwitterService {
	Collection<? extends TweetRepresentation> findNewerTweets(Hashtag hashtag, LocalDateTime lastTweet);

	Page<? extends TweetRepresentation> findPage(Hashtag hashtag, int pageCounter, int size);
}
