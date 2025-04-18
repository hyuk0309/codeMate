package org.personal.codymate.domain;

import java.util.Map;

public record LowestPriceSummaryByCategory(Map<Category, BrandInfo> categoryLowestPrices, Integer totalPrice) {
	public record BrandInfo(String name, Integer price) {}
}
