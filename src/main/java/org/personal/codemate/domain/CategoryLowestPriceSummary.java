package org.personal.codemate.domain;

import java.util.Map;

public record CategoryLowestPriceSummary(Map<Category, BrandInfo> categoryLowestPrices, Integer totalPrice) {
	public record BrandInfo(String name, Integer price) {}
}
