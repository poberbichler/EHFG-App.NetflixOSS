package org.ehfg.app.twitter;

import org.apache.commons.lang3.Validate;
import org.ehfg.app.base.ConfigurationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.search.ResultType;
import org.ehfg.app.search.SearchIndexDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author patrick
 * @since 03.2014
 */
@Component
final class TwitterFacadeImpl implements TwitterFacade, SearchIndexDataProvider<TweetDTO> {
    static final String TWITTER_URL = "http://EHFGAPP-TWITTER";

    private final RestOperations restTemplate;
    private final MasterDataFacade masterDataFacade;

    @Autowired
    public TwitterFacadeImpl(RestOperations restTemplate, MasterDataFacade masterDataFacade) {
        this.restTemplate = restTemplate;
        this.masterDataFacade = masterDataFacade;
    }

    @Override
    public Collection<String> findStreams() {
        return restTemplate.getForObject(TWITTER_URL + "/listener", Collection.class);
    }

    @Override
    public void addStream(String hashtag) {
        Validate.notNull(hashtag, "hashtag must not be null");
        restTemplate.postForObject(TWITTER_URL + "/listener/{hashtag}", null, List.class, hashtag);
    }

    @Override
    public void removeStream(String hashtag) {
        Validate.notNull(hashtag, "hashtag must not be null");
        restTemplate.delete(TWITTER_URL + "/listener/{hashtag}", hashtag);
    }

    @Override
    public String findHashtag() {
        return masterDataFacade.getAppConfiguration().getHashtag();
    }

    @Override
    public Collection<TweetDTO> findNewerTweetsForCongress(LocalDateTime lastTweet) {
        final ConfigurationDTO config = masterDataFacade.getAppConfiguration();
        if (config != null && config.getHashtag() != null) {
            return restTemplate.getForObject(TWITTER_URL + "/{hashtag}/{timestamp}", Collection.class, config.getHashtag(), lastTweet);
        }

        return Collections.emptyList();
    }

    @Override
    public Object findTweetPage(int pageId) {
        return findTweetPageWithSize(pageId, masterDataFacade.getAppConfiguration().getNumberOfTweets());
    }


    @Override
    public Object findTweetPageWithSize(int pageId, int pageSize) {
        Validate.notNull(pageId, "pageId must not be null!");
        Validate.notNull(pageSize, "pageSize must not be null!");

        String currentHashtag = findHashtag().substring(1);

        return restTemplate.getForObject(TWITTER_URL + "/tweets/{hashtag}/page/{pageCounter}/{size}", Object.class, currentHashtag, pageId, pageSize);

		/*
        final Page<Tweet> tweets = tweetRepository.findByHashtagOrderByCreationDateDesc(
				currentHashtag.toLowerCase(), new PageRequest(pageId, pageSize));

		return new TweetPageDTO(TweetMapper.map(tweets.getContent()), pageId, tweets.getTotalPages(), currentHashtag);
		*/
    }

    @Override
    public Collection<TwitterStatisticLine> findStats() {
        return Collections.emptyList();
        /*
        final List<Tweet> allTweets = tweetRepository.findAll();
        final Collection<SessionDTO> sessions = programFacade.findAllSessionsWithoutDayInformation();

        Map<SessionDTO, Integer> sessionTweetCountMap = new HashMap<>();
        allTweets.parallelStream().forEach(tweet ->
                sessions.stream()
                        .filter(session -> session.wasDuring(tweet.getCreationDate()))
                        .forEach(session -> {
                            sessionTweetCountMap.putIfAbsent(session, 0);
                            sessionTweetCountMap.computeIfPresent(session, (s, counter) -> counter++);
                        })
        );

        return sessionTweetCountMap.entrySet().stream()
                .map(entry -> new TwitterStatisticLine(entry.getKey().getNameWithCode(), entry.getValue()))
                .collect(toList());
        */
    }

    @Override
    public TwitterStreamStatus checkIfRelevantStreamIsRunning() {
        final Collection<String> currentStreams = findStreams();
        final String thisYearsHashtag = findHashtag();

        if (currentStreams.isEmpty() || !currentStreams.contains(thisYearsHashtag)) {
            addStream(thisYearsHashtag);
            return TwitterStreamStatus.HAD_TO_RESTART;
        }

        return TwitterStreamStatus.RUNNING;
    }

    @Override
    public List<TweetDTO> findTweetsForExport(String hashtag) {
        Validate.notNull(hashtag, "hashtag must not be null");
        return Collections.emptyList();

        /*
        return tweetRepository.findByHashtagIgnoreCase(hashtag)
                .parallelStream()
                .map(TweetMapper::mapUnformattedTweet)
                .collect(toList());
                */
    }

    @Override
    public Collection<? extends TweetDTO> getData() {
        return Collections.emptyList();
        /*
        return tweetRepository.findTop100ByHashtagOrderByCreationDateDesc(findHashtag())
				.stream()
				.map(TweetMapper::mapUnformattedTweet)
				.collect(toList());
				*/
    }

    @Override
    public Set<ResultType> getResultTypes() {
        return EnumSet.noneOf(ResultType.class);
    }
}
