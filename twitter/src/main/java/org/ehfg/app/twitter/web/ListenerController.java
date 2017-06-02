package org.ehfg.app.twitter.web;

import org.ehfg.app.twitter.data.Hashtag;
import org.ehfg.app.twitter.listener.TwitterListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author patrick
 * @since 10.2016
 */
@RestController
@RequestMapping("listener")
public class ListenerController {
	private final TwitterListenerService twitterListenerService;

	@Autowired
	public ListenerController(TwitterListenerService twitterListenerService) {
		this.twitterListenerService = twitterListenerService;
	}

	@GetMapping
	public Collection<String> findStreams() {
		return twitterListenerService.findAll();
	}

	@PostMapping("{streamName}")
	public Collection<String> addStream(@PathVariable("streamName") String streamName) {
		return twitterListenerService.addListener(Hashtag.valueOf(streamName));
	}

	@DeleteMapping("{streamName}")
	public Collection<String> removeStream(@PathVariable("streamName") String streamName) {
		return twitterListenerService.removeListener(Hashtag.valueOf(streamName));
	}
}
