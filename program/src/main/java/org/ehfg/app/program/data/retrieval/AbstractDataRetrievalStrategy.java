package org.ehfg.app.program.data.retrieval;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * Abstract strategy for retrieving data from the ehfg server <br>
 * to use it, this class has to be subclassed - that's it
 *
 * @author patrick
 * @since 06.2014
 */
public abstract class AbstractDataRetrievalStrategy<T> {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Resource dataResource;
	private JAXBContext jaxbContext;

	private final Class<T> fetchedClazz;

	/**
	 * @param fetchedClazz class to be searched for
	 */
	protected AbstractDataRetrievalStrategy(Class<T> fetchedClazz) {
		this.fetchedClazz = fetchedClazz;
	}

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		dataResource = getResource();
		jaxbContext = JAXBContext.newInstance(fetchedClazz);
	}

	public T fetchData() {
		try {
			logger.info("fetching data from {}", dataResource.getURL());

			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Object data = unmarshaller.unmarshal(dataResource.getInputStream());

			return fetchedClazz.cast(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final Class<T> getFetchedClass() {
		return fetchedClazz;
	}

	/**
	 * getter for the resource where the data should be feteched from (e.g. classpath or external url)
	 */
	protected abstract Resource getResource();
}
