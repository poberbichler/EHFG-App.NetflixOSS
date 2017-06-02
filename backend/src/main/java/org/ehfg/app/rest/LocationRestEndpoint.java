package org.ehfg.app.rest;

import org.ehfg.app.base.location.Location;
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.base.location.LocationRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author patrick
 * @since 07.2014
 */
@RestController
@RequestMapping("api/locations")
public final class LocationRestEndpoint {
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public LocationRestEndpoint(MasterDataFacade masterDataFacade) {
		this.masterDataFacade = masterDataFacade;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<? extends LocationRepresentation> findAll() {
		return masterDataFacade.findAllLocation();
	}
	
	@GetMapping(value = "{locationName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public LocationRepresentation findByName(@PathVariable("locationName") String locationName) {
		for (Location location : masterDataFacade.findAllLocation()) {
			if (location.getName().equalsIgnoreCase(locationName)) {
				return location;
			}
		}
		
		return null;
	}
}
