package org.personal.codymate.controller;

import java.util.List;
import org.personal.codymate.domain.UpsertBrandCommand;
import org.personal.codymate.domain.Category;

record UpsertBrandRequest(Long id, String name, List<Product> products) {
	record Product(Long id, Category category, Integer price){}

	public UpsertBrandCommand toCommand() {
		List<UpsertBrandCommand.Product> upsertBrandCommandProducts = this.products.stream()
			.map(p -> new UpsertBrandCommand.Product(p.id(), p.category(), p.price()))
			.toList();
		return new UpsertBrandCommand(id, name, upsertBrandCommandProducts);
	}
}
