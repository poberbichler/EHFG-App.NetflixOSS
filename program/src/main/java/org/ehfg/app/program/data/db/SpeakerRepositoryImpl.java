package org.ehfg.app.program.data.db;

import org.apache.commons.lang.StringUtils;
import org.ehfg.app.program.data.input.speaker.RssSpeaker;
import org.ehfg.app.program.data.input.speaker.Speaker;
import org.ehfg.app.program.data.output.SpeakerDTO;
import org.ehfg.app.program.data.retrieval.AbstractDataRetrievalStrategy;
import org.ehfg.app.program.service.SpeakerCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * @author patrick
 * @since 01.2015
 */
@Repository
class SpeakerRepositoryImpl implements SpeakerRepository {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final AbstractDataRetrievalStrategy<RssSpeaker> retrievalStrategy;

	@Autowired
	public SpeakerRepositoryImpl(AbstractDataRetrievalStrategy<RssSpeaker> retrievalStrategy) {
		this.retrievalStrategy = retrievalStrategy;
	}

	@Override
	@SpeakerCache
	public Collection<SpeakerDTO> findAll() {
		List<Speaker> speakers = retrievalStrategy.fetchData().getChannel().getSpeakers();
		logger.info("received {} speakers", speakers.size());

		Predicate<Speaker> hasFullName = (speaker -> isNotEmpty(speaker.getFirstname()) && isNotEmpty(speaker.getLastname()));

		return speakers.stream()
				.filter(hasFullName)
				.map(this::mapToDto)
				.sorted()
				.collect(Collectors.toList());
	}

	private SpeakerDTO mapToDto(Speaker speaker) {
		logger.trace("preparing text for speaker {}", speaker);
		String description = EscapeUtils.escapeText(speaker.getBio());
		description = StringUtils.remove(description.trim(), "<strong>");
		description = StringUtils.remove(description.trim(), "<div>");
		description = StringUtils.remove(description.trim(), "\n");
		description = StringUtils.removeStart(description.trim(), speaker.getFirstname());
		description = StringUtils.removeStart(description.trim(), speaker.getLastname());
		description = StringUtils.removeStart(description.trim(), speaker.getFirstname());
		description = StringUtils.removeStart(description.trim(), "</div>");
		description = StringUtils.removeStart(description.trim(), "</strong>");
		description = StringUtils.removeStart(description.trim(), ";");
		description = StringUtils.removeStart(description.trim(), ",");
		description = EscapeUtils.escapeLinks(description);

		String imagePath = speaker.getImagePath() == null ? "" : speaker.getImagePath().trim();
		return new SpeakerDTO(speaker.getId(), speaker.getFirstname(), speaker.getLastname(), description, imagePath);
	}
}
