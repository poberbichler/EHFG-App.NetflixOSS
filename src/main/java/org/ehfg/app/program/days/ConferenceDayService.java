package org.ehfg.app.program.days;

import java.util.Collection;

/**
 * @author patrick
 * @since 11.2016
 */
public interface ConferenceDayService {
	Collection<ConferenceDay> findDays();
	Collection<ConferenceDay> saveDay(ConferenceDay conferenceDay);
	Collection<ConferenceDay> replaceDays(Collection<ConferenceDay> days);
}
