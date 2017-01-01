package org.ehfg.app.rest;

import org.ehfg.app.twitter.TwitterFacade;
import org.ehfg.app.twitter.TwitterStreamStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author patrick
 * @since 04.2014
 *
 * @deprecated should be replaced by zuul
 */
@Deprecated
@RestController
@RequestMapping("api/twitter")
public final class TwitterRestEndpoint {
    private final TwitterFacade twitterFacade;

    @Autowired
    public TwitterRestEndpoint(TwitterFacade twitterFacade) {
        this.twitterFacade = twitterFacade;
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
