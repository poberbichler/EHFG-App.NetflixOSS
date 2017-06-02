package org.ehfg.app.program.data.retrieval.classpath;

import org.ehfg.app.program.data.input.speakerevents.RssSpeakerEvents;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 11.2016
 */
@Service
@ConditionalOnProperty("program.from.classpath")
public class ClasspathSpeakerEventRetrievalStrategy extends ClasspathBasedDataRetrievalStrategy<RssSpeakerEvents> {
	public ClasspathSpeakerEventRetrievalStrategy() {
		super(RssSpeakerEvents.class, "data/speaker-events.xml");
	}
}
