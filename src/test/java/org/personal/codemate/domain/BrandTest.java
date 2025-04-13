package org.personal.codemate.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BrandTest {

	@Nested
	class CreateNewBrandTest {
		@DisplayName("새로운 브랜드를 만든다.")
		@Test
		void createNewBrand() {
			// given
			List<UpsertBrandCommand.Product> products =
				IntStream.range(0, Category.values().length)
					.mapToObj(i -> {
						Category category = Category.values()[i];
						int price = 10000 + i * 1000; // 예시 가격
						return new UpsertBrandCommand.Product(null, category, price);
					})
					.collect(
						Collectors.toList());

			UpsertBrandCommand command = new UpsertBrandCommand(null, "Nike", products);

			// when
			Brand newBrand = Brand.createNewBrand(command);

			// then
			assertThat(newBrand).isNotNull();
		}

		@DisplayName("브랜드 이름이 없어서 생성에 실패한다.")
		@Test
		void createNewBrandFailBecauseInvalidName() {
			// given
			String emptyName = "";
			UpsertBrandCommand command = new UpsertBrandCommand(null, "Nike", List.of());

			// when then
			assertThatThrownBy(() -> Brand.createNewBrand(command))
				.isInstanceOf(IllegalArgumentException.class);
		}

		@DisplayName("브랜드에 상품이 존재하지 않아 생성에 실패한다.")
		@Test
		void createNewBrandFailBecauseEmptyProducts() {
			// given
			List<UpsertBrandCommand.Product> emptyProducts = Collections.emptyList();
			UpsertBrandCommand command = new UpsertBrandCommand(null, "Nike", emptyProducts);

			// when then
			assertThatThrownBy(() -> Brand.createNewBrand(command)).isInstanceOf(IllegalArgumentException.class);
		}

		@DisplayName("브랜드에 모든 카테고리 상품이 적어도 1개씩 존재하지 않아, 생성에 실패한다.")
		@Test
		void createNewBrandFailBecauseMissingCategoryProduct() {
			// given
			Category excludedCategory = Category.SOCKS;

			List<UpsertBrandCommand.Product> missingCategoryProducts =
				Stream.of(Category.values())
					.filter(category -> category != excludedCategory)
					.map(category -> {
						int price = 10000 + category.ordinal() * 1000;
						return new UpsertBrandCommand.Product(null, category, price);
					})
					.collect(Collectors.toList());

			UpsertBrandCommand command = new UpsertBrandCommand(null, "Nike", missingCategoryProducts);

			// when then
			assertThatThrownBy(() -> Brand.createNewBrand(command)).isInstanceOf(IllegalArgumentException.class);
		}
	}

}