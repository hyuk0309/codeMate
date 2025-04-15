package org.personal.codemate.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BrandChangeListener {

	private final LowestCategoryService lowestCategoryService;
	private final LowestBrandService lowestBrandService;

	@Async
	@EventListener
	void onBrandChange(BrandChangeEvent event) {
		lowestCategoryService.summarizeLowestPricesByCategory();
		lowestBrandService.loadLowestPriceBrandAndProductByCategory();
	}

}
