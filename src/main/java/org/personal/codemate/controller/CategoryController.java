package org.personal.codemate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.personal.codemate.domain.CategoryLowestPriceSummary;
import org.personal.codemate.domain.LowestCategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
class CategoryController {

	private final LowestCategoryService lowestCategoryService;

	@GetMapping("/v1.0/categories/lowest-prices")
	ResponseEntity<CategoryLowestPriceSummary> getLowestPriceProductsByCategory() {
		CategoryLowestPriceSummary categoryLowestPriceSummary = lowestCategoryService.getLowestPriceAndBrandSummary();
		return ResponseEntity.ok(categoryLowestPriceSummary);
	}

}
