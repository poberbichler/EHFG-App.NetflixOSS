package org.ehfg.app.twitter.service;

import org.ehfg.app.twitter.data.Hashtag;
import org.ehfg.app.twitter.data.Tweet;
import org.ehfg.app.twitter.data.TweetPage;
import org.ehfg.app.twitter.data.TweetRepresentation;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author patrick
 * @since 09.2016
 */
public interface TwitterService {
	Collection<? extends TweetRepresentation> findNewerTweets(Hashtag hashtag, LocalDateTime lastTweet);

	TweetPage findPage(Hashtag hashtag, int pageCounter, int size);

	Collection<Tweet> findForIndex();

	TweetPage findPage(int pageCounter, int size);

	Collection<? extends TweetRepresentation> findNewerTweets(LocalDateTime timestamp);
}
