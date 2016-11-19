package org.ehfg.app.program.data.retrieval;

import org.ehfg.app.program.data.input.speakerevents.RssSpeakerEvents;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 06.2014
 */
@Service
final class SpeakerEventRetrievalStrategy extends AbstractDataRetrievalStrategy<RssSpeakerEvents> {
	SpeakerEventRetrievalStrategy() {
		super(RssSpeakerEvents.class, "speakerevents");
	}
}
