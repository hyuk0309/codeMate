package org.personal.codymate.domain;

import java.util.List;

public record UpsertBrandCommand(Long id, String name, List<Product> products) {
	public record Product(Long id, Category category, Integer price) {}
}
