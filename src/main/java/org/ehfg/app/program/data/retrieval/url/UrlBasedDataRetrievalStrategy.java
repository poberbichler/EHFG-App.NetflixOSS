package org.ehfg.app.program.data.retrieval.url;

import org.ehfg.app.program.data.retrieval.AbstractDataRetrievalStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

/**
 * @author patrick
 * @since 11.2016
 */
@Service
@ConditionalOnProperty(name = "program.from.classpath", matchIfMissing = true, havingValue = "false")
public abstract class UrlBasedDataRetrievalStrategy<T> extends AbstractDataRetrievalStrategy<T> {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String URL_DELIMITER = "/";

	@Value("${rss.base.url}")
	private String baseUrl;

	@Value("${rss.sufix}")
	private String rssSufix;

	private final String urlSnippet;


	protected UrlBasedDataRetrievalStrategy(Class<T> fetchedClazz, String urlSnippet) {
		super(fetchedClazz);
		this.urlSnippet = urlSnippet;
	}

	@Override
	protected Resource getResource() {
		try {
			StringBuilder builder = new StringBuilder(baseUrl);
			if (!baseUrl.endsWith(URL_DELIMITER) && !urlSnippet.startsWith(URL_DELIMITER)) {
				builder.append("/");
			}

			builder.append(urlSnippet);
			if (!urlSnippet.endsWith(URL_DELIMITER)) {
				builder.append(URL_DELIMITER);
			}

			builder.append("data/");
			builder.append(rssSufix);

			return new UrlResource(builder.toString());
		} catch (MalformedURLException e) {
			logger.error("an error occured while instantiation class", e);
			throw new IllegalStateException(e);
		}
	}
}
