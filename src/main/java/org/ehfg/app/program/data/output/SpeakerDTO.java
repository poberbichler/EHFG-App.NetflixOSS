package org.ehfg.app.program.data.output;

import com.google.common.base.MoreObjects;

import java.util.Comparator;

/**
 * @author patrick
 * @since 11.2016
 */
public class SpeakerDTO implements SpeakerRepresentation, Comparable<SpeakerDTO> {
	private final String id;
	private final String firstName;
	private final String lastName;
	private final String description;
	private final String imageUrl;

	public SpeakerDTO(String id, String firstName, String lastName, String description, String imageUrl) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getDescription() {
		return description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public String getFullName() {
		return firstName + " " + lastName;
	}

	@Override
	public int compareTo(SpeakerDTO that) {
		return Comparator.comparing(SpeakerDTO::getFullName).compare(this, that);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("firstName", firstName)
				.add("lastName", lastName)
				.toString();
	}
}
