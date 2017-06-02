package org.ehfg.app.program.service;

import org.ehfg.app.program.data.output.ConferenceDayRepresentation;
import org.ehfg.app.program.data.output.SessionRepresentation;
import org.ehfg.app.program.data.output.SpeakerRepresentation;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

/**
 * @author patrick
 * @since 11.2016
 */
public interface ProgramService {
	Collection<? extends SessionRepresentation> findSessions();

	Collection<? extends SpeakerRepresentation> findSpeakers();

	Map<LocalDate, ? extends ConferenceDayRepresentation> findProgram();
}
