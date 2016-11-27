package org.ehfg.app.program.rest;

import org.ehfg.app.program.search.SearchResult;
import org.ehfg.app.program.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author patrick
 * @since 11.2016
 */
@RestController
@RequestMapping("search")
public class SearchController {
	private final SearchService searchService;

	@Autowired
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}

	@GetMapping({"{input}", "{input}/{maxResults}"})
	public SearchResult getSearchResult(@PathVariable("input") String input,
			@PathVariable(name = "maxResults") Optional<Integer> maxResults) {

		return searchService.search(input, maxResults.orElse(50));
	}

	@PostMapping
	public ResponseEntity updateIndex() {
		if (searchService.updateIndex()) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
