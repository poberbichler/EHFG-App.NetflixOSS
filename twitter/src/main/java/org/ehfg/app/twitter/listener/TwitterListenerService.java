package org.ehfg.app.twitter.listener;

import org.ehfg.app.twitter.data.Hashtag;

import java.util.Collection;

/**
 * @author patrick
 * @since 10.2016
 */
public interface TwitterListenerService {
	Collection<String> findAll();

	Collection<String> addListener(Hashtag streamName);

	Collection<String> removeListener(Hashtag streamName);
}
