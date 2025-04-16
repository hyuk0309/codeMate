package org.personal.codymate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.codymate.domain.Brand;
import org.personal.codymate.domain.BrandService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
class BrandController {

	private final BrandService brandService;

	@PostMapping("/v1.0/brands")
	ResponseEntity<UpsertBrandResponse> brandUpsert(@RequestBody UpsertBrandRequest request) {
		Brand savedBrand = brandService.upsertBrand(request.toCommand());
		return ResponseEntity.ok(UpsertBrandResponse.of(savedBrand));
	}
}
