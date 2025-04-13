package org.personal.codemate.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

	@DisplayName("상품 생성에 성공한다.")
	@Test
	void createNewProduct() {
		// given
		UpsertBrandCommand.Product product = new UpsertBrandCommand.Product(null, Category.TOP, 1000);
		Brand brand = new Brand();

		// when
		Product newProduct = Product.newProduct(product, brand);

		// then
		assertThat(newProduct).isNotNull();
	}

	@DisplayName("카테고리가 존재하지 않아 상품 생성에 실패한다.")
	@Test
	void createProductFailBecauseCategoryIsNull() {
		// given
		Category nullCategory = null;
		UpsertBrandCommand.Product product = new UpsertBrandCommand.Product(null, nullCategory, 1000);
		Brand brand = new Brand();

		// when then
		assertThatThrownBy(() -> Product.newProduct(product, brand)).isInstanceOf(InvalidRequestException.class);
	}

	@DisplayName("가격이 존재하지 않아 상품 생성에 실패한다.")
	@Test
	void createProductFailBecausePriceIsNull() {
		// given
		Integer invalidPrice = null;
		UpsertBrandCommand.Product product = new UpsertBrandCommand.Product(null, Category.TOP, invalidPrice);
		Brand brand = new Brand();

		// when then
		assertThatThrownBy(() -> Product.newProduct(product, brand)).isInstanceOf(InvalidRequestException.class);
	}

}