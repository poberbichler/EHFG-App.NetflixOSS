package org.ehfg.app.config;

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.ehfg.app.converter.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author patrick
 * @since 11.2014
 */
@EnableWebMvc
@Configuration
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource(ignoreResourceNotFound = true, value = {"file:////${user.home}/ehfg.properties"})
public class WebConfig extends WebMvcConfigurerAdapter {
	@Value("${absolute.mobile.path:}")
	private String absoluteMobilePath;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/rest/**");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToLocalDateConverter());
		registry.addConverter(new LocalDateToStringConverter());
		registry.addConverter(new LongToLocalDateTimeConverter());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/eureka/css/**").addResourceLocations("classpath:/static/eureka/css/");
		registry.addResourceHandler("/eureka/js/**").addResourceLocations("classpath:/static/eureka/js/");
		registry.addResourceHandler("/eureka/fonts/**").addResourceLocations("classpath:/static/eureka/fonts/");
		registry.addResourceHandler("/eureka/images/**").addResourceLocations("classpath:/static/eureka/images/");

		if (!StringUtils.isEmpty(absoluteMobilePath)) {
			registry.addResourceHandler("/mobile/**").addResourceLocations(absoluteMobilePath);
		}
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.indentOutput(true).modulesToInstall(new JaxbAnnotationModule());
		builder.serializerByType(LocalDateTime.class, new LocalDateTimeToUTCTimestampSerializer());

		converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
		converters.add(new StringHttpMessageConverter());
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("versions");
		return messageSource;
	}

	@Bean
	public AsyncRestTemplate asyncRestTemplate() {
		AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
		return asyncRestTemplate;
	}
}
