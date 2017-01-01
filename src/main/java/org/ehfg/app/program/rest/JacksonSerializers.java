package org.ehfg.app.program.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.time.*;
import java.util.Map;

/**
 * @author patrick
 * @since 05.2015
 */
class JacksonSerializers {
	private static final Map<Class<?>, JsonSerializer<?>> serializerMap = ImmutableMap.<Class<?>, JsonSerializer<?>>builder()
			.put(LocalDateTime.class, new LocalDateTimeToUTCTimestampSerializer())
			.build();

	public static Map<Class<?>, JsonSerializer<?>> getSerializers() {
		return serializerMap;
	}

	private static final class LocalDateTimeToUTCTimestampSerializer extends JsonSerializer<LocalDateTime> {
		@Override
		public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
			if (value != null) {
				final ZonedDateTime zonedInput = value.atZone(ZoneId.of("UTC"));
				generator.writeNumber(zonedInput.toInstant().toEpochMilli());
			}
		}
	}
}
