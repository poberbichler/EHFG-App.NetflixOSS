package org.ehfg.app.base.location;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author patrick
 * @since 06.2015
 */
public interface LocationRepository extends MongoRepository<Location, String> {
	// empty
}
