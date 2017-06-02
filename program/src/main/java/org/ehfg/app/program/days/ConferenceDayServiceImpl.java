package org.ehfg.app.program.days;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author patrick
 * @since 11.2016
 */
@Service
class ConferenceDayServiceImpl implements ConferenceDayService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private ConferenceDayRepository conferenceDayRepository;

	@Autowired
	public ConferenceDayServiceImpl(ConferenceDayRepository conferenceDayRepository) {
		this.conferenceDayRepository = conferenceDayRepository;
	}

	@Override
	public Collection<ConferenceDay> findDays() {
		return conferenceDayRepository.findAll();
	}

	@Override
	public Collection<ConferenceDay> saveDay(ConferenceDay conferenceDay) {
		checkNotNull(conferenceDay, "conferenceDay must not be null");
		logger.info("adding day [{}]", conferenceDay);

		if (conferenceDay.getId() != null) {
			ConferenceDay persistedDay = conferenceDayRepository.findOne(conferenceDay.getId());
			checkNotNull(persistedDay, "no conferenceday with id [%s] found", conferenceDay.getId());

			persistedDay.setDescription(conferenceDay.getDescription());
			persistedDay.setDay(conferenceDay.getDay());
			conferenceDayRepository.save(persistedDay);
		} else {
			conferenceDayRepository.save(conferenceDay);
		}

		return findDays();
	}

	@Override
	public Collection<ConferenceDay> replaceDays(Collection<ConferenceDay> days) {
		checkNotNull(days, "days must not be null");
		logger.info("replacing the current conferencedays with these [{}]", days);

		if (conferenceDayRepository.count() > 0) {
			conferenceDayRepository.deleteAll();
		}

		return conferenceDayRepository.save(days);
	}
}
