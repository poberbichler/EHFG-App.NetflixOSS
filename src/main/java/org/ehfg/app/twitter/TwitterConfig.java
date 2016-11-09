package org.ehfg.app.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.social.TwitterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * @author patrick
 * @since 09.2016
 */
@Configuration
public class TwitterConfig extends SocialConfigurerAdapter {
    @Autowired
    private TwitterProperties twitterProperties;

    @Bean
    public Twitter twitter(@Value("${twitter.access.token}") String accessToken,
                           @Value("${twitter.access.secret}") String accessTokenSecret) {
        return new SimpleClientHttpRequestFactoryTwitterTemplate(
                twitterProperties.getAppId(), twitterProperties.getAppSecret(),
                accessToken, accessTokenSecret);
    }

    /**
     * Custom implementation of {@link TwitterTemplate} reconfiguring the internal {@link RestTemplate} to use Spring's own
     * {@link ClientHttpRequestFactory} instead of the one provided by Apache HttpClient.<br>
     * For whatever reason, when the Apache implementation is used, the provided {@link StreamListener} is not called for every Tweet
     *
     * @see ClientHttpRequestFactorySelector#getRequestFactory()
     */
    private static class SimpleClientHttpRequestFactoryTwitterTemplate extends TwitterTemplate {
        private SimpleClientHttpRequestFactoryTwitterTemplate(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
            super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        }

        @Override
        protected void configureRestTemplate(RestTemplate restTemplate) {
            restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
        }
    }
}
