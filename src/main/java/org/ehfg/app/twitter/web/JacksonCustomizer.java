package org.ehfg.app.twitter.web;

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

/**
 * @author patrick
 * @since 10.2016
 */
@Component
public class JacksonCustomizer implements Jackson2ObjectMapperBuilderCustomizer {
	@Override
	public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		jacksonObjectMapperBuilder.modulesToInstall(new JaxbAnnotationModule());
		jacksonObjectMapperBuilder.indentOutput(true);
	}
}
