package org.ehfg.app.program.data.retrieval.classpath;

import org.ehfg.app.program.data.retrieval.AbstractDataRetrievalStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 11.2016
 */
@Service
@ConditionalOnProperty("program.from.classpath")
abstract class ClasspathBasedDataRetrievalStrategy<T> extends AbstractDataRetrievalStrategy<T>{
	private final String fileName;

	protected ClasspathBasedDataRetrievalStrategy(Class<T> fetchedClazz, String fileName) {
		super(fetchedClazz);
		this.fileName = fileName;
	}

	@Override
	protected Resource getResource() {
		return new ClassPathResource(fileName);
	}
}
