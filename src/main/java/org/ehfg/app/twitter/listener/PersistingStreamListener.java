package org.ehfg.app.twitter.listener;

import org.ehfg.app.twitter.data.*;
import org.ehfg.app.twitter.service.TweetRepository;
import org.ehfg.app.twitter.service.TwitterUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.Tweet;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

/**
 * @author patrick
 * @since 10.2016
 */
public class PersistingStreamListener implements StreamListener {
	private final Logger logger;

	private final Hashtag hashtag;

	private final TweetRepository tweetRepository;
	private final TwitterUserRepository twitterUserRepository;

	public PersistingStreamListener(Hashtag hashtag, TweetRepository tweetRepository, TwitterUserRepository twitterUserRepository) {
		this.hashtag = checkNotNull(hashtag);
		this.tweetRepository = checkNotNull(tweetRepository);
		this.twitterUserRepository = checkNotNull(twitterUserRepository);

		this.logger = LoggerFactory.getLogger(format("%s [%s]", getClass(), hashtag.getHashtagWithHash()));
	}

	@Override
	public void onTweet(Tweet sourceTweet) {
		checkNotNull(sourceTweet, "sourceTweet must not be null");

		TwitterProfile sourceUser = sourceTweet.getUser();
		final TwitterUser author = twitterUserRepository.findOne(Long.toString(sourceUser.getId())).orElseGet(() -> {
			logger.info("adding new user with id [{}]", sourceUser.getId());

			TwitterUser twitterUser = new TwitterUser();
			twitterUser.setId(Long.toString(sourceUser.getId()));
			return twitterUser;
		});

		author.setFullName(sourceUser.getName());
		author.setNickName(sourceUser.getScreenName());
		author.setProfileImage(sourceUser.getProfileImageUrl());
		twitterUserRepository.save(author);

		if (sourceTweet.isRetweet()) {
			logger.info("tweet got retweeted...");
			org.ehfg.app.twitter.data.Tweet retweetedTweet = tweetRepository.findOne(Long.toString(sourceTweet.getRetweetedStatus().getId()));
			if (retweetedTweet == null) {
				logger.warn("no tweet with id [{}] found that could have been retweeted", sourceTweet.getRetweetedStatus().getId());
			} else {
				logger.info("tweet [{}] got retweeted", retweetedTweet.getId());
				retweetedTweet.addRetweet(sourceUser.getScreenName());
				tweetRepository.save(retweetedTweet);
			}
		}

		tweetRepository.save(TweetFactory.create(sourceTweet, hashtag.getHashtagForDb(), author));
	}

	@Override
	public void onDelete(StreamDeleteEvent deleteEvent) {
		logger.warn("delete event will not be handled [{}]", deleteEvent);
	}

	@Override
	public void onLimit(int numberOfLimitedTweets) {
		logger.warn("limit event will not be handled [{}]", numberOfLimitedTweets);
	}

	@Override
	public void onWarning(StreamWarningEvent warningEvent) {
		logger.warn("warn event will not be handled [{}]", warningEvent);
	}
}
