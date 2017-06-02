package org.ehfg.app.program.data.retrieval.classpath;

import org.ehfg.app.program.data.input.speaker.RssSpeaker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 11.2016
 */
@Service
@ConditionalOnProperty("program.from.classpath")
public class ClasspathSessionRetrievalStrategy extends ClasspathBasedDataRetrievalStrategy<RssSpeaker> {
	protected ClasspathSessionRetrievalStrategy() {
		super(RssSpeaker.class, "data/speaker.xml");
	}
}
