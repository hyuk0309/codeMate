package org.personal.codemate.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

	@Nested
	class UpdateBrandTest {
		@DisplayName("브랜드 이름을 수정한다.")
		@Test
		void updateBrandName() {
			// given
			Brand brand = createNewBrand();

			String newBrandName = "Google";
			List<UpsertBrandCommand.Product> productsToUpdate = brand.getProducts().stream()
				.map(product -> new UpsertBrandCommand.Product(product.getId(), product.getCategory(), product.getPrice()))
				.toList();
			UpsertBrandCommand command = new UpsertBrandCommand(brand.getId(), newBrandName, productsToUpdate);

			// when
			brand.update(command);

			// then
			assertThat(brand.getName()).isEqualTo(newBrandName);
		}

		@DisplayName("브랜드 이름을 수정한다.")
		@Test
		void updateBrandNameFailBecauseInvalidName() {
			// given
			Brand brand = createNewBrand();

			String newBrandName = "";
			List<UpsertBrandCommand.Product> productsToUpdate = brand.getProducts().stream()
				.map(product -> new UpsertBrandCommand.Product(product.getId(), product.getCategory(), product.getPrice()))
				.toList();
			UpsertBrandCommand command = new UpsertBrandCommand(brand.getId(), newBrandName, productsToUpdate);

			// when then
			assertThatThrownBy(() -> brand.update(command)).isInstanceOf(IllegalArgumentException.class);
		}

		@DisplayName("브랜드의 기존 상품을 변경한다.")
		@Test
		void updateBrandProduct() {
			// given
			Brand brand = createNewBrand();
			Integer newPrice = 5000;
			List<UpsertBrandCommand.Product> productsToUpdate = brand.getProducts().stream()
				.map(product -> new UpsertBrandCommand.Product(product.getId(), product.getCategory(), newPrice))
				.toList();
			UpsertBrandCommand command = new UpsertBrandCommand(brand.getId(), brand.getName(), productsToUpdate);

			// when
			brand.update(command);

			// then
			assertThat(brand.getProducts())
				.extracting(Product::getPrice)
				.allMatch(price -> Objects.equals(price, newPrice));
		}

		@DisplayName("유효하지 카테고리 상품으로 update 한다.")
		@Test
		void updateBrandProductFailBecauseInvalidCategoryProduct() {
			// given
			Brand brand = createNewBrand();
			List<UpsertBrandCommand.Product> productsToUpdate = brand.getProducts().stream()
				.map(product -> new UpsertBrandCommand.Product(product.getId(), Category.SOCKS, product.getPrice()))
				.toList();
			UpsertBrandCommand command = new UpsertBrandCommand(brand.getId(), brand.getName(), productsToUpdate);

			// when then
			assertThatThrownBy(() -> brand.update(command)).isInstanceOf(IllegalArgumentException.class);
		}

		@DisplayName("잘못된 정보의 상품으로 업데이트한다.")
		@Test
		void updateBrandProductFailBecauseInvalidProductInfo() {
			// given
			Brand brand = createNewBrand();
			List<UpsertBrandCommand.Product> productsToUpdate = brand.getProducts().stream()
				.map(product -> new UpsertBrandCommand.Product(product.getId(), null, null))
				.toList();
			UpsertBrandCommand command = new UpsertBrandCommand(brand.getId(), brand.getName(), productsToUpdate);

			// when then
			assertThatThrownBy(() -> brand.update(command)).isInstanceOf(IllegalArgumentException.class);
		}

		@DisplayName("브랜드에 새로운 상품을 추가한다")
		@Test
		void addNewProductToBrand() {
			// given
			Brand brand = createNewBrand();
			int prevProductCounts = brand.getProducts().size();

			List<UpsertBrandCommand.Product> productsToUpdate = brand.getProducts().stream()
				.map(product -> new UpsertBrandCommand.Product(product.getId(), product.getCategory(), product.getPrice()))
				.collect(Collectors.toList());
			productsToUpdate.add(new UpsertBrandCommand.Product(null, Category.TOP, 1000));

			UpsertBrandCommand command = new UpsertBrandCommand(brand.getId(), brand.getName(), productsToUpdate);

			// when
			brand.update(command);

			// then
			assertThat(brand.getProducts().size()).isGreaterThan(prevProductCounts);
		}

		@DisplayName("브랜드에 있던 상품을 삭제한다")
		@Test
		void removeProductFromBrand() {
			// given
			Brand brand = createNewBrand();
			Category categoryToDelete = Category.TOP;
			brand.getProducts().add(Product.newProduct(new UpsertBrandCommand.Product(null, categoryToDelete, 1000), brand));

			int prevProductCounts = brand.getProducts().size();

			List<UpsertBrandCommand.Product> productsToUpdate = brand.getProducts().stream()
				.map(product -> new UpsertBrandCommand.Product(product.getId(), product.getCategory(), product.getPrice()))
				.collect(Collectors.toList());

			for (UpsertBrandCommand.Product product : productsToUpdate) {
				if (product.category().equals(categoryToDelete)) {
					productsToUpdate.remove(product);
					break;
				}
			}

			UpsertBrandCommand command = new UpsertBrandCommand(brand.getId(), brand.getName(), productsToUpdate);

			// when
			brand.update(command);

			// then
			assertThat(brand.getProducts().size()).isLessThan(prevProductCounts);
		}

		private Brand createNewBrand() {
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
			return Brand.createNewBrand(command);
		}
	}

}