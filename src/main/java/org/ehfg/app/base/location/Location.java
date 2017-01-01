package org.ehfg.app.base.location;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.base.Coordinate;
import org.ehfg.app.base.point.PointOfInterest;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * @author patrick
 * @since 07.2014
 */
@Document
public class Location implements LocationRepresentation {
	@Id
	private String id;

	@NotNull
	private String name;

	@DBRef
	private PointOfInterest point;

	private Coordinate coordinate;

	@Transient
	private String selectedPointOfInterestId;

	public Location() {
		// no arg ctor needed for editing
	}

	public Location(String id, String name, PointOfInterest point) {
		this.id = id;
		this.name = name;
		this.point = point;
	}

	public Location(String id, String name, Coordinate coordinate) {
		this.id = id;
		this.name = name;
		this.coordinate = coordinate;
	}

	public Location(String id, String name, Double xValue, Double yValue) {
		this(id, name, new Coordinate(xValue, yValue));
	}

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
	public Coordinate getCoordinate() {
		return coordinate;
	}

	@Override
	public String getPointOfInterestId() {
		if (point == null) {
			return null;
		}

		return point.getId();
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public PointOfInterest getPoint() {
		return point;
	}

	public void setPoint(PointOfInterest point) {
		this.point = point;
	}

	public String getSelectedPointOfInterestId() {
		return selectedPointOfInterestId;
	}

	public void setSelectedPointOfInterestId(String selectedPointOfInterestId) {
		this.selectedPointOfInterestId = selectedPointOfInterestId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
