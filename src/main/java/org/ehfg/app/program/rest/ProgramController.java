package org.ehfg.app.program.rest;

import org.ehfg.app.program.data.output.ConferenceDayRepresentation;
import org.ehfg.app.program.data.output.SessionRepresentation;
import org.ehfg.app.program.data.output.SpeakerRepresentation;
import org.ehfg.app.program.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

/**
 * @author patrick
 * @since 11.2016
 */
@RestController
public class ProgramController {
	private final ProgramService programService;

	@Autowired
	public ProgramController(ProgramService programService) {
		this.programService = programService;
	}

	@GetMapping("sessions")
	public Collection<? extends SessionRepresentation> getSessions() {
		return programService.findSessions();
	}

	@GetMapping("speakers")
	public Collection<? extends SpeakerRepresentation> getSpeakers() {
		return programService.findSpeakers();
	}

	@GetMapping("program")
	public Map<LocalDate, ? extends ConferenceDayRepresentation> getProgram() {
		return programService.findProgram();
	}
}
