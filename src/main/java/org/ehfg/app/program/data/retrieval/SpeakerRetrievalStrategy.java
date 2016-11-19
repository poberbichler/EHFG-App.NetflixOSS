package org.ehfg.app.program.data.retrieval;

import org.ehfg.app.program.data.input.speaker.RssSpeaker;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 06.2014
 */
@Service
final class SpeakerRetrievalStrategy extends AbstractDataRetrievalStrategy<RssSpeaker> {
	SpeakerRetrievalStrategy() {
		super(RssSpeaker.class, "speakers");
	}
}
