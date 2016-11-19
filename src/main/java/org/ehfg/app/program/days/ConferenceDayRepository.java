package org.ehfg.app.program.days;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author patrick
 * @since 11.2016
 */
interface ConferenceDayRepository extends MongoRepository<ConferenceDay, String> {
	// empty
}
