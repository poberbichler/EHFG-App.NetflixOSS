package org.ehfg.app.twitter.service;

import org.ehfg.app.twitter.data.TwitterUser;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * @author patrick
 * @since 09.2016
 */
public interface TwitterUserRepository extends Repository<TwitterUser, String> {
	Optional<TwitterUser> findOne(String id);

	TwitterUser save(TwitterUser user);
}
