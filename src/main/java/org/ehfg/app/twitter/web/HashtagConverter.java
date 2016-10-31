package org.ehfg.app.twitter.web;

import org.ehfg.app.twitter.data.Hashtag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author patrick
 * @since 10.2016
 */
@Component
public class HashtagConverter implements Converter<String, Hashtag> {
	@Override
	public Hashtag convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}

		return Hashtag.valueOf(source);
	}
}
