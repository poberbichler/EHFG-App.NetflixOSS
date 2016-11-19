package org.ehfg.app.program.rest;

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

/**
 * @author patrick
 * @since 11.2016
 */
@Component
public class JacksonCustomizer implements Jackson2ObjectMapperBuilderCustomizer {
	@Override
	public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		jacksonObjectMapperBuilder.modulesToInstall(new JaxbAnnotationModule());
		jacksonObjectMapperBuilder.indentOutput(true);
		jacksonObjectMapperBuilder.serializersByType(JacksonSerializers.getSerializers());
		jacksonObjectMapperBuilder.deserializersByType(JacksonSerializers.getDeserializers());
	}
}
