package org.ehfg.app.twitter.search;

import org.ehfg.app.twitter.data.Tweet;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author patrick
 * @since 11.2016
 */
public class SearchResult {
	private Collection<Tweet> tweets = new LinkedList<>();

	SearchResult addItem(Tweet tweet) {
		tweets.add(tweet);
		return this;
	}

	public Collection<Tweet> getTweets() {
		return tweets;
	}
}
