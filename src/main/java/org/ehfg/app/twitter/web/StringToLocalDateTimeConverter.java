package org.ehfg.app.twitter.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author patrick
 * @since 10.2016
 */
@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime>{
	@Override
	public LocalDateTime convert(String source) {
		if (source == null) {
			return LocalDateTime.now();
		}

		final LocalDateTime utcTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(source)), ZoneId.of("UTC"));
		return utcTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().minusHours(2);
	}
}
