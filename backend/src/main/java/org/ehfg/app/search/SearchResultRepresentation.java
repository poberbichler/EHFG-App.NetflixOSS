package org.ehfg.app.search;

import org.ehfg.app.twitter.TweetDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Collection;

/**
 * @author patrick
 * @since 06.2016
 */
@XmlAccessorType(XmlAccessType.NONE)
public interface SearchResultRepresentation {
    @XmlElement(name = "hasAnyResult")
    boolean hasAnyResult();

	@XmlElement(name = "results")
	Collection<SearchResult.SearchResultData> getResults();

	@XmlElement(name = "tweets")
	Collection<TweetDTO> getTweets();

	@XmlAccessorType(XmlAccessType.NONE)
	interface SearchResultDataRepresentation {
		@XmlElement(name = "type")
		ResultType getType();

		@XmlElement(name = "data")
		Collection<SearchResultItem> getResults();
	}
}
