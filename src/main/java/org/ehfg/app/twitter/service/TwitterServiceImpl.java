package org.ehfg.app.twitter.service;

import org.ehfg.app.twitter.data.Hashtag;
import org.ehfg.app.twitter.data.Tweet;
import org.ehfg.app.twitter.data.TweetPage;
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
	private static final Sort ORDER_BY_CREATION_DATE = new Sort(new Sort.Order(Sort.Direction.DESC, "creationDate"));

	private final TweetRepository tweetRepository;

	@Autowired
	public TwitterServiceImpl(TweetRepository tweetRepository) {
		this.tweetRepository = tweetRepository;
	}


	@Override
	public Collection<? extends TweetRepresentation> findNewerTweets(Hashtag hashtag, LocalDateTime lastTweetTimestamp) {
		return tweetRepository.findNewerTweetsByHashtag(hashtag.getHashtagForDb(), lastTweetTimestamp, ORDER_BY_CREATION_DATE);
	}

	@Override
	public TweetPage findPage(Hashtag hashtag, int pageCounter, int size) {
		Page<? extends TweetRepresentation> page = tweetRepository.findByHashtagOrderByCreationDateDesc(hashtag.getHashtagForDb(), new PageRequest(pageCounter, size));
		return new TweetPage(page, hashtag.getHashtagWithHash());
	}

	@Override
	public Collection<Tweet> findForIndex() {
		return tweetRepository.findAll(new PageRequest(0, 250)).getContent();
	}

	@Override
	public TweetPage findPage(int pageCounter, int size) {
		Page<Tweet> page = tweetRepository.findAll(new PageRequest(pageCounter, size, ORDER_BY_CREATION_DATE));
		return new TweetPage(page, page.getContent().get(0).getHashtag());
	}

	@Override
	public Collection<? extends TweetRepresentation> findNewerTweets(LocalDateTime timestamp) {
		return tweetRepository.findNewerTweets(timestamp, ORDER_BY_CREATION_DATE);

	}
}
