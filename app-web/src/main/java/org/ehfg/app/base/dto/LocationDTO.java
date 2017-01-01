package org.ehfg.app.base.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.base.Coordinate;
import org.ehfg.app.base.LocationMappingValid;
import org.ehfg.app.base.PointOfInterest;
import org.ehfg.app.rest.LocationRepresentation;

import javax.validation.constraints.NotNull;

/**
 * basic representation of a location
 * 
 * @author patrick
 * @since 03.2014
 * @deprecated use {@link org.ehfg.app.base.Location} instead
 */
@Deprecated
@LocationMappingValid
public class LocationDTO implements LocationRepresentation {
	private String id;
	@NotNull
	private String name;
	
	private Coordinate coordinate;
	private PointOfInterest mappedPointOfInterest;

	public LocationDTO() {
		coordinate = new Coordinate();
	}

	public LocationDTO(String id, String name, Coordinate coordinate) {
		this.id = id;
		this.name = name;
		this.coordinate = coordinate;
	}
	
	public LocationDTO(String id, String name, Double xValue, Double yValue) {
		this(id, name, new Coordinate(xValue, yValue));
	}

    public LocationDTO(String id, String name, PointOfInterest pointOfInterestDTO) {
        this.id = id;
        this.name = name;
        this.mappedPointOfInterest = pointOfInterestDTO;
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
		return mappedPointOfInterest != null ? mappedPointOfInterest.getId() : null;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

    public PointOfInterest getMappedPointOfInterest() {
        return mappedPointOfInterest;
    }

    public void setMappedPointOfInterest(PointOfInterest mappedPointOfInterest) {
        this.mappedPointOfInterest = mappedPointOfInterest;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
