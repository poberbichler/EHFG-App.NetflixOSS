package org.ehfg.app.program;

import java.util.List;

/**
 * @author patrick
 * @since 11.2014
 */
public class ConferenceDayForm {
	private List<ConferenceDayDTO> days;

	// default ctor, needed by various frameworks :(
	public ConferenceDayForm() {

	}

	public ConferenceDayForm(List<ConferenceDayDTO> days) {
		this.days = days;
	}
	
	public List<ConferenceDayDTO> getDays() {
		return days;
	}
	
	public void setDays(List<ConferenceDayDTO> days) {
		this.days = days;
	}
}
