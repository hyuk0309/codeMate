package org.personal.codymate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.personal.codymate.domain.Category;
import org.personal.codymate.domain.CategoryLowestPriceSummary;
import org.personal.codymate.domain.LowestBrandCody;
import org.personal.codymate.domain.LowestBrandService;
import org.personal.codymate.domain.LowestCategoryService;
import org.personal.codymate.domain.RecommendCategoryCody;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	/**
	 * 카테고리의 최저가, 취고가 브랜드 및 상품 조회 API
	 */
	@GetMapping("/v1.0/cody/categories/{category}")
	ResponseEntity<RecommendCategoryCody> getProductByCategory(@PathVariable("category") Category category) {
		return ResponseEntity.ok(lowestCategoryService.getCategoryProduct(category));
	}

}
