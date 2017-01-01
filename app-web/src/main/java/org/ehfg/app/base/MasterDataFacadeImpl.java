package org.ehfg.app.base;

import org.apache.commons.lang3.StringUtils;
import org.ehfg.app.base.config.AppConfig;
import org.ehfg.app.base.config.AppConfigRepository;
import org.ehfg.app.base.dto.LocationDTO;
import org.ehfg.app.base.dto.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<LocationDTO> findAllLocation() {
		return locationRepository.findAll().stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
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

	private LocationDTO mapToDto(Location input) {
		if (input == null) {
			return null;
		}

		if (input.getPoint() != null) {
			return new LocationDTO(input.getId(), input.getName(), input.getPoint());
		}

		return new LocationDTO(input.getId(), input.getName(), input.getCoordinate().getxValue(), input.getCoordinate().getyValue());
	}

	@Override
	public String saveLocation(LocationDTO source) {
		Location target = null;
		if (source.getMappedPointOfInterest() != null && !StringUtils.isEmpty(source.getMappedPointOfInterest().getId())) {
			PointOfInterest point = pointOfInterestRepository.findOne(source.getMappedPointOfInterest().getId());
			target = new Location(source.getId(), source.getName(), point);

		} else {
			target = new Location(source.getId(), source.getName(),
					source.getCoordinate().getxValue(), source.getCoordinate().getyValue());
		}

		locationRepository.save(target);
		return target.getId();
	}

	@Override
	public void deleteLocation(String locationId) {
		locationRepository.delete(locationId);
	}
}
