package org.ehfg.app.base.location;

import org.ehfg.app.base.CoordinateRepresentation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author patrick
 * @since 04.2015
 */
@XmlAccessorType(XmlAccessType.NONE)
public interface LocationRepresentation {
	@XmlElement(name = "id")
	String getId();

	@XmlElement(name = "name")
	String getName();

	@XmlElement(name = "coordinate")
	CoordinateRepresentation getCoordinate();

	@XmlElement(name = "pointId")
	String getPointOfInterestId();
}
