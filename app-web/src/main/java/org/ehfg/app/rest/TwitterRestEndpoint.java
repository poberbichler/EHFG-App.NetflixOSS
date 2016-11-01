package org.ehfg.app.rest;

import org.ehfg.app.twitter.TweetPageDTO;
import org.ehfg.app.twitter.TwitterFacade;
import org.ehfg.app.twitter.TwitterStreamStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author patrick
 * @since 04.2014
 */
@RestController
@RequestMapping("rest/twitter")
public final class TwitterRestEndpoint {
	private final TwitterFacade twitterFacade;
	private final RestTemplate restTemplate;

	@Autowired
	public TwitterRestEndpoint(TwitterFacade twitterFacade, RestTemplate restTemplate) {
		this.twitterFacade = twitterFacade;
		this.restTemplate = restTemplate;
	}

	@GetMapping
	@RequestMapping(value = "hashtag", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Object> getHashtag() {
		return restTemplate.getForObject("http://EHFGAPP-TWITTER/tweets/asdf", List.class);
	}

	@RequestMapping(value = "update/{lastTweet}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<? extends TweetRepresentation> updateTweets(@PathVariable("lastTweet") LocalDateTime timestamp) {
		return twitterFacade.findNewerTweetsForCongress(timestamp);
	}

	@RequestMapping(value = "page/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public TweetPageDTO findTweetsByPage(@PathVariable("page") Integer pageId) {
		return twitterFacade.findTweetPage(pageId);
	}
	
	@RequestMapping(value = "check", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String checkIfRunning() {
		TwitterStreamStatus status = twitterFacade.checkIfRelevantStreamIsRunning();
		
		switch (status) {
			case HAD_TO_RESTART:
				return "stream had to be restarted";
				
			case RUNNING:
				return "stream was already running";
				
			case NOT_RUNNING:
			default:
				return "thats bad, bro";
		}
	}
}
