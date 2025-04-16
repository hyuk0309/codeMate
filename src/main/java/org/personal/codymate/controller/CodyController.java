package org.personal.codymate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.personal.codymate.domain.Category;
import org.personal.codymate.domain.LowestPriceSummaryByCategory;
import org.personal.codymate.domain.LowestPriceSummaryByBrand;
import org.personal.codymate.domain.CodyService;
import org.personal.codymate.domain.TopAndBottomPriceProduct;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 고객에게 제공되는 API
 */
@Slf4j
@RestController
@RequiredArgsConstructor
class CodyController {

	private final CodyService codyService;

	/**
	 * 카테고리별 최저가 상품으로 가능한 코디 조합 제공 API
	 */
	@GetMapping("/v1.0/cody/categories/lowest-price-summary")
	ResponseEntity<LowestPriceSummaryByCategory> getLowestPriceProductsByCategory() {
		LowestPriceSummaryByCategory lowestPriceSummaryByCategory = codyService.getLowestPriceSummaryByCategory();
		return ResponseEntity.ok(lowestPriceSummaryByCategory);
	}

	/**
	 * 단일 브랜드로 최저 가격으로 가능한 코디 조합 제공 API
	 */
	@GetMapping("/v1.0/cody/brands/lowest-price-summary")
	ResponseEntity<LowestBrandCodyResponse> getLowestPriceProductsBySingleBrand() {
		LowestPriceSummaryByBrand lowestPriceSummaryByBrand = codyService.getLowestPriceSummaryByBrand();
		return ResponseEntity.ok(new LowestBrandCodyResponse(lowestPriceSummaryByBrand));
	}

	/**
	 * 특정 카테고리 최저가, 최고가 상품 제공 API
	 */
	@GetMapping("/v1.0/cody/categories/{category}/top-bottom-price")
	ResponseEntity<TopAndBottomPriceProduct> getProductByCategory(@PathVariable("category") Category category) {
		return ResponseEntity.ok(codyService.findTopAndBottomPriceProducts(category));
	}

}
