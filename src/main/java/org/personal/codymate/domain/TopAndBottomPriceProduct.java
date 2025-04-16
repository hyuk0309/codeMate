package org.personal.codymate.domain;

public record TopAndBottomPriceProduct(
	String category,
	ProductInfo bottom,
	ProductInfo top
) {
	public record ProductInfo(String brand, Integer price) {}
}
