package org.ehfg.app.program.data.db;

import org.ehfg.app.program.search.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Task to clean every {@link Cache} of the configured {@link CacheManager} every hour<br>
 * After this hour, the {@link Cache caches} will be initialized again
 *
 * @author patrick
 * @since 01.2015
 */
@Service
class CacheResettingTask {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final CacheManager cacheManager;
	private final SessionRepository sessionRepository;
	private final SpeakerRepository speakerRepository;

	private final SearchService searchService;

	@Autowired
	public CacheResettingTask(CacheManager cacheManager, SessionRepository sessionRepository, SpeakerRepository speakerRepository,
							  SearchService searchService) {
		this.cacheManager = cacheManager;
		this.sessionRepository = sessionRepository;
		this.speakerRepository = speakerRepository;
		this.searchService = searchService;
	}

	@Scheduled(fixedRate = 1000 * 60 * 60) // hourly
	public void resetCaches() {
		logger.info("going to clean every cache");

		for (String cacheName : cacheManager.getCacheNames()) {
			logger.debug("cleaning cache [{}]", cacheName);
			cacheManager.getCache(cacheName).clear();
		}

		sessionRepository.findAll();
		speakerRepository.findAll();

		searchService.updateIndex();
	}
}
