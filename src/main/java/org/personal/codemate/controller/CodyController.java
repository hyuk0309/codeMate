package org.personal.codemate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.personal.codemate.domain.CategoryLowestPriceSummary;
import org.personal.codemate.domain.LowestBrandCody;
import org.personal.codemate.domain.LowestBrandService;
import org.personal.codemate.domain.LowestCategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
class CodyController {

	private final LowestCategoryService lowestCategoryService;
	private final LowestBrandService lowestBrandService;

	/**
	 * 카테고리별 최저가 브랜드와 상품 조회 API
	 */
	@GetMapping("/v1.0/categories/lowest-prices")
	ResponseEntity<CategoryLowestPriceSummary> getLowestPriceProductsByCategory() {
		CategoryLowestPriceSummary categoryLowestPriceSummary = lowestCategoryService.getLowestPriceAndBrandSummary();
		return ResponseEntity.ok(categoryLowestPriceSummary);
	}

	/**
	 * 단일 브랜드 최저가 조합 조회 API
	 */
	@GetMapping("/v1.0/brands/lowest-combi")
	ResponseEntity<LowestBrandCodyResponse> getLowestPriceProductsBySingleBrand() {
		LowestBrandCody lowestBrandCody = lowestBrandService.getLowestPriceAndBrandSummary();
		return ResponseEntity.ok(new LowestBrandCodyResponse(lowestBrandCody));
	}

}
