package org.ehfg.app.twitter.service;

import org.ehfg.app.twitter.data.Tweet;
import org.ehfg.app.twitter.data.TweetRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author patrick
 * @since 09.2016
 */
public interface TweetRepository extends MongoRepository<Tweet, String> {
	@Query("{hashtag: ?0, creationDate: {$gt: ?1}}")
	List<Tweet> findNewerTweetsByHashtag(String hashtag, LocalDateTime lastTweet, Sort sort);

	@Query("{creationDate: {$gt: ?1}}")
	Collection<? extends TweetRepresentation> findNewerTweets(LocalDateTime timestamp, Sort sort);

	Page<Tweet> findByHashtagOrderByCreationDateDesc(String hashtagForDb, Pageable pageRequest);

}
