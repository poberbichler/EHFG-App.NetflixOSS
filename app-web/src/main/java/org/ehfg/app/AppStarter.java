package org.ehfg.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
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

    /**
     * Using the injected {@link RestTemplateBuilder} is the best way to build the {@link RestTemplate} with spring boot,
     * otherwise the order of the internal {@link org.springframework.http.converter.HttpMessageConverter}
     * will be in the "wrong" order. <p>
     * The property {@link org.springframework.http.HttpHeaders#setContentType(MediaType)} will be set by the first
     * {@link org.springframework.http.converter.HttpMessageConverter} which is capable of doing so, so in this case
     * the contentType of the request will be set to {@link MediaType#APPLICATION_XML} if the {@link RestTemplateBuilder}
     * was not used.<p>
     * Furthermore, {@link org.springframework.boot.web.client.RestTemplateCustomizer} will not be picked up without the {@link RestTemplateBuilder}
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
