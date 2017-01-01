package org.ehfg.app.base.dto;

import org.ehfg.app.base.AppConfig;

import java.util.Collection;
import java.util.List;

/**
 * @author patrick
 * @since 03.2014
 */
public interface MasterDataFacade {
	AppConfig getAppConfiguration();
	AppConfig saveAppConfiguration(AppConfig config);

	List<PointOfInterestDTO> findAllPointsOfInterest();
	List<PointOfInterestDTO> savePointOfInterest(PointOfInterestDTO source);
	void removePoint(String id);

	Collection<MapCategoryDTO> findAllMapCategories();
	void saveMapCategory(MapCategoryDTO categoryDTO);
	void deleteMapCategory(String id);

	List<LocationDTO> findAllLocation();
	String saveLocation(LocationDTO newLocation);
	void deleteLocation(String locationId);
}
