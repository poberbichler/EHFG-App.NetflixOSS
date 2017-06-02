package org.ehfg.app.twitter.data;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.social.twitter.api.*;

import java.util.List;

/**
 * Wrapper class for {@link Page}, adding the additional fields required by the frontent
 *
 * @author patrick
 * @since 11.2016
 */
public class TweetPage {
    private final String currentHashtag;

    @JsonUnwrapped
    private final Page<? extends TweetRepresentation> tweetPage;

    public TweetPage(Page<? extends TweetRepresentation> page, String currentHashtag) {
        this.tweetPage = page;
        this.currentHashtag = currentHashtag;
    }

    public boolean isMorePages() {
        return !tweetPage.isLast();
    }

    public Page<? extends TweetRepresentation> getTweetPage() {
        return tweetPage;
    }

    public String getCurrentHashtag() {
        return currentHashtag;
    }
}
