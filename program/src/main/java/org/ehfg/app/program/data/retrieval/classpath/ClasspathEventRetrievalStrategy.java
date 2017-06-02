package org.ehfg.app.program.data.retrieval.classpath;

import org.ehfg.app.program.data.input.events.RssEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 11.2016
 */
@Service
@ConditionalOnProperty("program.from.classpath")
public class ClasspathEventRetrievalStrategy extends ClasspathBasedDataRetrievalStrategy<RssEvent> {
	protected ClasspathEventRetrievalStrategy() {
		super(RssEvent.class, "data/events.xml");
	}
}
