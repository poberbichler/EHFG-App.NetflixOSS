package org.ehfg.app.twitter.web;

import org.ehfg.app.twitter.data.Hashtag;
import org.ehfg.app.twitter.data.TweetRepresentation;
import org.ehfg.app.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collection;

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

    @GetMapping("{hashtag}/{timestamp}")
    public Collection<? extends TweetRepresentation> findNewer(@PathVariable("hashtag") Hashtag hashtag, @PathVariable("timestamp") LocalDateTime lastTweet) {
        return twitterService.findNewerTweets(hashtag, lastTweet);
    }

    @GetMapping("{hashtag}/page/{pageCounter}/{size}")
    public Page<? extends TweetRepresentation> getPage(@PathVariable("hashtag") Hashtag hashtag, @PathVariable("pageCounter") int pageCounter, @PathVariable("size") int size) {
        return twitterService.findPage(hashtag, pageCounter, size);
    }
}
