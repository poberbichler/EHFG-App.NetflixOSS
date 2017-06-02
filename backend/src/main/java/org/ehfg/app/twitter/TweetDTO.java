package org.ehfg.app.twitter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import org.ehfg.app.converter.NumberTimestampLocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author patrick
 * @since 20.03.2014
 */
public class TweetDTO implements Comparable<TweetDTO> {
	private final String id;
	private final String fullName;
	private final String nickName;
	private final String message;
	private final String profileImage;
	private final boolean retweet;
	private final Collection<String> retweetedBy;

	@JsonDeserialize(using = NumberTimestampLocalDateTimeDeserializer.class)
	private final LocalDateTime timestamp;

	@JsonCreator
	public TweetDTO(@JsonProperty("id") String id,
					@JsonProperty("fullName") String fullName,
					@JsonProperty("nickName") String nickName,
					@JsonProperty("message") String message,
					@JsonProperty("profileImage") String profileImage,
					@JsonProperty("timestamp") LocalDateTime timestamp,
					@JsonProperty("retweet") boolean retweet,
					@JsonProperty("retweetedBy") Collection<String> retweetedBy) {
		this.id = id;
		this.fullName = fullName;
		this.nickName = nickName;
		this.message = message;
		this.profileImage = profileImage;
		this.timestamp = timestamp;
		this.retweet = retweet;
		this.retweetedBy = retweetedBy;
	}

	public String getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public String getNickName() {
		return nickName;
	}

	public String getMessage() {
		return message;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public boolean isRetweet() {
		return retweet;
	}

	public Collection<String> getRetweetedBy() {
		return retweetedBy;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("nickName", nickName)
				.add("timestamp", timestamp)
				.add("message", message)
				.add("retweetedBy", retweetedBy)
				.toString();
	}

	@Override
	public int compareTo(TweetDTO that) {
		return this.getTimestamp().compareTo(that.getTimestamp());
	}
}
