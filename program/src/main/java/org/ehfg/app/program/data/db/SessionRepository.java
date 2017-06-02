package org.ehfg.app.program.data.db;

import org.ehfg.app.program.data.output.SessionDTO;

import java.util.Collection;

/**
 * @author patrick
 * @since 06.2014
 */
public interface SessionRepository {
	/**
	 * @return a collection of {@link SessionDTO} (never null)
	 */
	Collection<SessionDTO> findAll();
}
