package org.ehfg.app.program.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
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
			.put(LocalDate.class, new LocalDatetoUTCTimestampSerializer())
			.put(LocalDateTime.class, new LocalDateTimeToUTCTimestampSerializer())
			.build();

	private static final Map<Class<?>, JsonDeserializer<?>> deserializerMap = ImmutableMap.<Class<?>, JsonDeserializer<?>>builder()
			.put(LocalDate.class, new LocalDateDeserializer())
			.build();

	public static Map<Class<?>, JsonSerializer<?>> getSerializers() {
		return serializerMap;
	}

	public static Map<Class<?>, JsonDeserializer<?>> getDeserializers() {
		return deserializerMap;
	}

	private static final class LocalDatetoUTCTimestampSerializer extends JsonSerializer<LocalDate> {
		@Override
		public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
			if (value != null) {
				generator.writeNumber(value.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli());
			}
		}
	}

	private static final class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
		@Override
		public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
			return Instant.ofEpochMilli(jsonParser.getValueAsLong()).atZone(ZoneId.of("UTC")).toLocalDate();
		}
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
