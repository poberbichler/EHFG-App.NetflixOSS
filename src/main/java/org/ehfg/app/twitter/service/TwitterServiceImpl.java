package org.ehfg.app.twitter.service;

import org.ehfg.app.twitter.data.Hashtag;
import org.ehfg.app.twitter.data.TweetRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author patrick
 * @since 09.2016
 */
@Service
class TwitterServiceImpl implements TwitterService {
	private static final Sort CREATION_DATE = new Sort(new Sort.Order(Sort.Direction.DESC, "creationDate"));

	private final TweetRepository tweetRepository;

	@Autowired
	public TwitterServiceImpl(TweetRepository tweetRepository) {
		this.tweetRepository = tweetRepository;
	}


	@Override
	public Collection<? extends TweetRepresentation> findNewerTweets(Hashtag hashtag, LocalDateTime lastTweet) {
		return tweetRepository.findNewerTweetsByHashtag(hashtag.getHashtagForDb(), lastTweet, CREATION_DATE);
	}

	@Override
	public Page<? extends TweetRepresentation> findPage(Hashtag hashtag, int pageCounter, int size) {
		return tweetRepository.findByHashtagOrderByCreationDateDesc(hashtag.getHashtagForDb(), new PageRequest(pageCounter, size));
	}
}
