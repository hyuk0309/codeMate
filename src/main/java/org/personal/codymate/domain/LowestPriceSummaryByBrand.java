package org.personal.codymate.domain;

import java.util.List;

public record LowestPriceSummaryByBrand(String brandName, List<ProductInfo> category, Integer totalPrice) {
	public record ProductInfo(String category, Integer price) {}
}
