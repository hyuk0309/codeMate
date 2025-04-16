package org.personal.codymate.domain;

public record RecommendCategoryCody(
	String category,
	ProductInfo lowest,
	ProductInfo highest
) {
	public record ProductInfo(String brand, Integer price) {}
}
