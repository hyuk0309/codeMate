package org.personal.codymate.controller;

import java.util.Arrays;
import org.personal.codymate.domain.Category;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements Converter<String, Category> {
	@Override
	public Category convert(String source) {
		return Arrays.stream(Category.values())
			.filter(category -> category.name().equalsIgnoreCase(source))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
