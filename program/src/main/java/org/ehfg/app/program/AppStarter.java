package org.ehfg.app.program;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author patrick
 * @since 11.2016
 */
@EnableCaching
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
