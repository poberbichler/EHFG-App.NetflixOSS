package org.ehfg.app.base;

import org.ehfg.app.base.category.MapCategory;
import org.ehfg.app.base.category.MapCategoryRepository;
import org.ehfg.app.base.config.AppConfig;
import org.ehfg.app.base.config.AppConfigRepository;
import org.ehfg.app.base.location.Location;
import org.ehfg.app.base.location.LocationRepository;
import org.ehfg.app.base.point.PointOfInterest;
import org.ehfg.app.base.point.PointOfInterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author patrick
 * @since 14.03.2014
 */
@Component
class MasterDataFacadeImpl implements MasterDataFacade {
	private final AppConfigRepository configRepository;
	private final PointOfInterestRepository pointOfInterestRepository;
	private final LocationRepository locationRepository;
	private final MapCategoryRepository mapCategoryRepository;

	@Autowired
	public MasterDataFacadeImpl(AppConfigRepository configRepository, PointOfInterestRepository pointOfInterestRepository, LocationRepository locationRepository, MapCategoryRepository mapCategoryRepository) {
		this.configRepository = configRepository;
		this.pointOfInterestRepository = pointOfInterestRepository;
		this.locationRepository = locationRepository;
		this.mapCategoryRepository = mapCategoryRepository;
	}

	@Override
	public AppConfig getAppConfiguration() {
		return configRepository.find().orElse(AppConfig.withDefaultValues());
	}

	@Override
	public AppConfig saveAppConfiguration(AppConfig source) {
		return configRepository.save(source);
	}

	@Override
	public Collection<PointOfInterest> findAllPointsOfInterest() {
		return pointOfInterestRepository.findAll();
	}

	@Override
	public Collection<PointOfInterest> savePointOfInterest(PointOfInterest source) {
		checkNotNull(source, "source must not be null");

		source.setMapCategory(mapCategoryRepository.findByName(source.getCategoryName()));
		pointOfInterestRepository.save(source);

		return pointOfInterestRepository.findAll();
	}

	@Override
	public void removePoint(String id) {
		pointOfInterestRepository.delete(id);
	}

	@Override
	public List<Location> findAllLocation() {
		return locationRepository.findAll();
	}

	@Override
	public Collection<MapCategory> findAllMapCategories() {
		return mapCategoryRepository.findAll();
	}

	@Override
	public void saveMapCategory(MapCategory category) {
		checkNotNull(category, "source must not be null");
		mapCategoryRepository.save(category);
	}

	@Override
	public void deleteMapCategory(String id) {
		mapCategoryRepository.delete(id);
	}

	@Override
	public Location saveLocation(Location location) {
		checkNotNull(location, "source must not be null");

		if (location.getSelectedPointOfInterestId() != null) {
			location.setPoint(pointOfInterestRepository.findOne(location.getSelectedPointOfInterestId()));
		}

		return locationRepository.save(location);
	}

	@Override
	public void deleteLocation(String locationId) {
		locationRepository.delete(locationId);
	}
}
