package org.personal.codymate.domain;

import java.util.List;

public record LowestBrandCody(String brandName, List<ProductInfo> category, Integer totalPrice) {
	public record ProductInfo(String category, Integer price) {}
}
