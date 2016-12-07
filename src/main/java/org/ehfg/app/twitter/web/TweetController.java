package org.ehfg.app.twitter.web;

import org.ehfg.app.twitter.data.Hashtag;
import org.ehfg.app.twitter.data.TweetPage;
import org.ehfg.app.twitter.data.TweetRepresentation;
import org.ehfg.app.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * @author patrick
 * @since 09.2016
 */
@RestController
@RequestMapping("tweets")
public class TweetController {
    private final TwitterService twitterService;

    @Autowired
    public TweetController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @GetMapping("page/{pageCounter}")
	public TweetPage getPage(@PathVariable("pageCounter") int pageCounter,
							 @RequestParam(name = "size", required = false, defaultValue = "25") int pageSize,
							 @RequestParam(name="hashtag", required = false) Optional<Hashtag> hashtag) {

		if (hashtag.isPresent()) {
			return twitterService.findPage(hashtag.get(), pageCounter, pageSize);
		}
		return twitterService.findPage(pageCounter, pageSize);
	}

	@GetMapping("update/{timestamp}")
	public Collection<? extends TweetRepresentation> findNewer(@PathVariable("timestamp") LocalDateTime timestamp,
															   @RequestParam(name = "hashtag", required = false) Optional<Hashtag> hashtag) {

		if (hashtag.isPresent()) {
			return twitterService.findNewerTweets(hashtag.get(), timestamp);
		}

 		return twitterService.findNewerTweets(timestamp);
	}
}
