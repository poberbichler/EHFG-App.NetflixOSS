package org.ehfg.app.program;

import java.util.Collection;
import java.util.List;

/**
 * @author patrick
 * @since 06.04.2014
 */
public interface ProgramFacade {
	Collection<SpeakerDTO> findAllSpeakers();

	List<ConferenceDayDTO> findDays();

	void saveDays(List<ConferenceDayDTO> dayList);

	Collection<SessionDTO> findAllSessionsWithoutDayInformation();
	
	List<String> findAvailableLocations();
}
