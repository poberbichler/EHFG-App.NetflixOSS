package org.ehfg.app.mvc.maintenance;

import org.ehfg.app.base.PointOfInterest;
import org.ehfg.app.base.dto.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author patrick
 * @since 11.2014
 */
@Controller
@RequestMapping("maintenance/pointofinterest")
public class PointOfInterestController {
	private static final String TO_BASE_PAGE = "redirect:/maintenance/pointofinterest";
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public PointOfInterestController(MasterDataFacade masterDataFacade) {
		this.masterDataFacade = masterDataFacade;
	}

	@GetMapping
	public ModelAndView getPage() {
		ModelAndView view = new ModelAndView("pointOfInterest");
		view.addObject("activePage", "pointOfInterest");
		view.addObject("points", masterDataFacade.findAllPointsOfInterest());
		view.addObject("categories", masterDataFacade.findAllMapCategories());
		view.addObject("editPoint", new PointOfInterest());

		return view;
	}

	@PostMapping
	public String addPoint(@Valid @ModelAttribute("editPoint") PointOfInterest point, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			masterDataFacade.savePointOfInterest(point);
		}
		return TO_BASE_PAGE;
	}

	@GetMapping("edit/{id}")
	public ModelAndView editPointOfInterest(@PathVariable("id") final String pointId) {
		Collection<PointOfInterest> allPoints = masterDataFacade.findAllPointsOfInterest();

		ModelAndView view = new ModelAndView("pointOfInterest");
		view.addObject("editPoint", findPointInList(allPoints, pointId));
		view.addObject("points", allPoints);
		view.addObject("activePage", "pointOfInterest");
		view.addObject("categories", masterDataFacade.findAllMapCategories());

		return view;
	}

	@GetMapping("delete/{pointId}")
	public String deletePointOfInterest(@PathVariable("pointId") String pointId) {
		masterDataFacade.removePoint(pointId);
		return TO_BASE_PAGE;
	}

	private PointOfInterest findPointInList(Collection<PointOfInterest> points, String pointId) {
		return points.stream().filter(p -> p.getId().equals(pointId)).findFirst().get();
	}
}
