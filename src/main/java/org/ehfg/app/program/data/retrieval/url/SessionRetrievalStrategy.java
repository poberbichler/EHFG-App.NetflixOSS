package org.ehfg.app.program.data.retrieval.url;

import org.ehfg.app.program.data.input.events.RssEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 06.2014
 */
@Service
@ConditionalOnProperty(name = "program.from.classpath", matchIfMissing = true, havingValue = "false")
class SessionRetrievalStrategy extends UrlBasedDataRetrievalStrategy<RssEvent> {
	SessionRetrievalStrategy() {
		super(RssEvent.class, "events");
	}
}
