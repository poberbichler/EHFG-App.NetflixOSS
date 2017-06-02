package org.ehfg.app.program.data.db;

import org.ehfg.app.program.data.output.SpeakerDTO;

import java.util.Collection;

/**
 * @author patrick
 * @since 06.2014
 */
public interface SpeakerRepository {
	/**
	 * @return collection of {@link SpeakerDTO} (never null)
	 */
	Collection<SpeakerDTO> findAll();
}
