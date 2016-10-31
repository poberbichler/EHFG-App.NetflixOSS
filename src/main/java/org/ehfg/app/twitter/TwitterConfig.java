package org.ehfg.app.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.social.TwitterProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

/**
 * @author patrick
 * @since 09.2016
 */
@Configuration
public class TwitterConfig {
	@Autowired
	private TwitterProperties twitterProperties;

	@Bean
	public Twitter twitter(@Value("${twitter.access.token}")String accessToken,
						   @Value("${twitter.access.secret}") String accessTokenSecret) {
		return new TwitterTemplate(twitterProperties.getAppId(), twitterProperties.getAppSecret(),
				accessToken, accessTokenSecret);
	}
}
