package org.ehfg.app.program.days;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * @author patrick
 * @since 11.2016
 */
@Document
public class ConferenceDay {
	@Id
	private String id;

	private LocalDate date;
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object thatObject) {
		if (this == thatObject) {
			return true;
		}

		if (!(thatObject instanceof ConferenceDay)) {
			return false;
		}

		ConferenceDay that = (ConferenceDay) thatObject;
		return Objects.equal(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("date", date)
				.add("description", description)
				.toString();
	}
}
