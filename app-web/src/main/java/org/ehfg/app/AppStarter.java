package org.ehfg.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Starter class that bootstraps the servlet container, and starts the ehfg backend
 *
 * @author patrick
 * @since 05.2015
 */
@EnableEurekaServer
@EnableEurekaClient
@SpringBootApplication
public class AppStarter extends SpringBootServletInitializer {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppStarter.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AppStarter.class);
    }

    @Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
