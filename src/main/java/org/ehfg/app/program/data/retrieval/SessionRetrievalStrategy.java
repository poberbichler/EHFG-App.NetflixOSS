package org.ehfg.app.program.data.retrieval;

import org.ehfg.app.program.data.input.events.RssEvent;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 06.2014
 */
@Service
final class SessionRetrievalStrategy extends AbstractDataRetrievalStrategy<RssEvent> {
	SessionRetrievalStrategy() {
		super(RssEvent.class, "events");
	}
}
