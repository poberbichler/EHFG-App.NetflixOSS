package org.ehfg.app.program;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * basic represenation of a speaker
 *
 * @author patrick
 * @since 01.2014
 */
public class SpeakerDTO {
	private final String id;
	private final String firstName;
	private final String lastName;
	private final String description;
	private final String imageUrl;

	@JsonCreator
	public SpeakerDTO(@JsonProperty("id") String id,
					  @JsonProperty("firstName") String firstName,
					  @JsonProperty("lastName") String lastName,
					  @JsonProperty("description") String description,
					  @JsonProperty("imageUrl") String imageUrl) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public String getFullName() {
		return firstName + " " + lastName;
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
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("firstName", firstName)
				.add("lastName", lastName)
				.toString();
	}
}
