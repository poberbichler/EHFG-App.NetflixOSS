package org.ehfg.app.base.dto;

import org.ehfg.app.base.PointOfInterest;
import org.ehfg.app.base.config.AppConfig;

import java.util.Collection;
import java.util.List;

/**
 * @author patrick
 * @since 03.2014
 */
public interface MasterDataFacade {
	AppConfig getAppConfiguration();
	AppConfig saveAppConfiguration(AppConfig config);

	Collection<PointOfInterest> findAllPointsOfInterest();
	Collection<PointOfInterest> savePointOfInterest(PointOfInterest source);
	void removePoint(String id);

	Collection<MapCategoryDTO> findAllMapCategories();
	void saveMapCategory(MapCategoryDTO categoryDTO);
	void deleteMapCategory(String id);

	List<LocationDTO> findAllLocation();
	String saveLocation(LocationDTO newLocation);
	void deleteLocation(String locationId);
}
