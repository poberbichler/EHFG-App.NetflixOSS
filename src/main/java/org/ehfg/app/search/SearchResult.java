package org.ehfg.app.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.ehfg.app.twitter.TweetDTO;

import java.util.Collection;

import static java.util.Collections.emptyList;

/**
 * @author patrick
 * @since 06.2016
 */
public class SearchResult implements SearchResultRepresentation {
	private final Collection<TweetDTO> tweets;
	private final Collection<SearchResultData> results;

	public static SearchResult empty() {
		return new SearchResult(null, null);
	}

	@JsonCreator
	public SearchResult(@JsonProperty("tweets") Collection<TweetDTO> tweets,
						@JsonProperty("results") Collection<SearchResultData> results) {
		this.tweets = (tweets != null) ? tweets : emptyList();
		this.results = (results != null) ? results : emptyList();
	}

	@Override
    public boolean hasAnyResult() {
        return !results.isEmpty() || !tweets.isEmpty();
    }

    @Override
	public Collection<SearchResultData> getResults() {
		return results;
	}

	@Override
	public Collection<TweetDTO> getTweets() {
		return tweets;
	}

	public static class SearchResultData implements SearchResultDataRepresentation {
		private final ResultType type;
		private final Collection<SearchResultItem> results;

		@JsonCreator
		public SearchResultData(@JsonProperty("type") ResultType type,
								@JsonProperty("data") Collection<SearchResultItem> results) {
			this.type = type;
			this.results = results;
		}

		@Override
		public ResultType getType() {
			return type;
		}

		@Override
		public Collection<SearchResultItem> getResults() {
			return this.results;
		}
	}
}
