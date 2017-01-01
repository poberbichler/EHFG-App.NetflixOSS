package org.ehfg.app.base;

import com.google.common.base.MoreObjects;
import org.ehfg.app.rest.MapCategoryRepresentation;
import org.ehfg.app.rest.PointOfInterestRepresentation;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author patrick
 * @since 06.2015
 */
@Document
public class PointOfInterest implements PointOfInterestRepresentation {
	@Id
	private String id;

	@NotNull
	private String name;

	@NotNull
	private String description;

	private String address;
	private String contact;

	private String website;

	private MapCategory mapCategory;

	@Valid
	@NotNull
	private Coordinate coordinate;

	@Transient
	private String categoryName;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Coordinate getCoordinate() {
		return coordinate;
	}

	@Override
	public MapCategoryRepresentation getCategory() {
		return null;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	@Override
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public MapCategory getMapCategory() {
		return mapCategory;
	}

	public void setMapCategory(MapCategory mapCategory) {
		this.mapCategory = mapCategory;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("name", name)
				.toString();
	}

}
