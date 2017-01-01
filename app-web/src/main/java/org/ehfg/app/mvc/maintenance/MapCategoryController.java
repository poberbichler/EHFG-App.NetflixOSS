package org.ehfg.app.mvc.maintenance;

import org.ehfg.app.base.MapCategory;
import org.ehfg.app.base.dto.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

/**
 * @author poberbichler
 * @since 08.2016
 */
@Controller
@RequestMapping("maintenance/mapcategory")
public class MapCategoryController {
    private final MasterDataFacade masterDataFacade;

    @Autowired
    public MapCategoryController(MasterDataFacade masterDataFacade) {
        this.masterDataFacade = masterDataFacade;
    }

    @GetMapping({"", "{id}"})
    public ModelAndView getPage(@PathVariable("id") Optional<String> categoryId) {
        ModelAndView view = new ModelAndView("mapcategory");

        view.addObject("activePage", "mapcategory");
        Collection<MapCategory> allCategories = masterDataFacade.findAllMapCategories();
        view.addObject("categories", allCategories);

        MapCategory editCategory = allCategories.stream()
                .filter(c -> c.getId().equals(categoryId.orElseGet(String::new)))
                .findFirst()
                .orElseGet(MapCategory::new);

        view.addObject("editCategory", editCategory);
        return view;
    }

    @PostMapping
    public String saveCategory(@Valid MapCategory mapCategory) {
        masterDataFacade.saveMapCategory(mapCategory);
        return "redirect:/maintenance/mapcategory";
    }

    @GetMapping("delete/{id}")
    public String deleteCategory(@PathVariable("id") String categoryId) {
        masterDataFacade.deleteMapCategory(categoryId);
        return "redirect:/maintenance/mapcategory";
    }
}
