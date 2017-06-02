package org.ehfg.app.program.rest;

import org.ehfg.app.program.days.ConferenceDay;
import org.ehfg.app.program.days.ConferenceDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author patrick
 * @since 11.2016
 */
@RestController
@RequestMapping("conferencedays")
public class ConferenceDayController {
	private final ConferenceDayService conferenceDayService;

	@Autowired
	public ConferenceDayController(ConferenceDayService conferenceDayService) {
		this.conferenceDayService = conferenceDayService;
	}

	@GetMapping
	public Collection<ConferenceDay> getDays() {
		return conferenceDayService.findDays();
	}

	@PostMapping
	public Collection<ConferenceDay> replaceDays(@RequestBody List<ConferenceDay> days) {
		return conferenceDayService.replaceDays(days);
	}

	@PutMapping
	public Collection<ConferenceDay> addDay(@RequestBody ConferenceDay conferenceDay) {
		return conferenceDayService.saveDay(conferenceDay);
	}
}
