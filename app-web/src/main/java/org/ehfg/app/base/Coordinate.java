package org.ehfg.app.base;

import com.google.common.base.MoreObjects;

import javax.validation.constraints.NotNull;

/**
 * @author patrick
 * @since 13.04.2014
 */
public class Coordinate implements CoordinateRepresentation {
	@NotNull
	private Double xValue;

	@NotNull
	private Double yValue;

	public Coordinate() {

	}

	public Coordinate(Double xValue, Double yValue) {
		this.xValue = xValue;
		this.yValue = yValue;
	}

	public Double getxValue() {
		return xValue;
	}

	public void setxValue(Double xValue) {
		this.xValue = xValue;
	}

	public Double getyValue() {
		return yValue;
	}

	public void setyValue(Double yValue) {
		this.yValue = yValue;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("xValue", xValue)
				.add("yValue", yValue)
				.toString();
	}

	@Override
	public double getLatitude() {
		return xValue;
	}

	@Override
	public double getLongitude() {
		return yValue;
	}
}
