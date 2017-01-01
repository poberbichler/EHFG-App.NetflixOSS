package org.ehfg.app.base.point;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author patrick
 * @since 06.2015
 */
public interface PointOfInterestRepository extends MongoRepository<PointOfInterest, String> {
	// empty
}
