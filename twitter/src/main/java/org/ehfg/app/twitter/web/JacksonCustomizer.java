package org.ehfg.app.twitter.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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
        jacksonObjectMapperBuilder.mixIn(Page.class, PageMixin.class);
        jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeToUTCTimestampSerializer());
    }

    private static abstract class PageMixin {
        @JsonProperty("data")
        abstract List<?> getContent();

        @JsonProperty("maxPages")
        abstract int getTotalPages();

        @JsonProperty("currentPage")
        abstract int getNumber();
    }
}
