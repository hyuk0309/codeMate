package org.personal.codemate.controller;

import java.util.List;
import org.personal.codemate.domain.Brand;
import org.personal.codemate.domain.Category;

record UpsertBrandResponse(Long id, String name, List<Product> products) {
	record Product(Long id, Category category, Integer price){}

	static UpsertBrandResponse of(Brand brand) {
		List<Product> products = brand.getProducts().stream()
			.map(p -> new Product(p.getId(), p.getCategory(), p.getPrice()))
			.toList();

		return new UpsertBrandResponse(brand.getId(), brand.getName(), products);
	}
}
