package org.ehfg.app.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author patrick
 * @since 09.2016
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AppStarter extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(AppStarter.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AppStarter.class);
	}
}
