package org.personal.codemate.domain;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Getter
@ToString(exclude = "brand")
@Entity(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // for JPA
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;

	private Integer price;

	/**
	 * 새로운 상품을 만든다.
	 * @param command 새로운 상품 정보
	 * @return 새로운 상품
	 */
	static Product newProduct(UpsertBrandCommand.Product command, Brand brand) {
		if (Objects.nonNull(command.id()) || Objects.isNull(command.category()) || Objects.isNull(command.price())) {
			throw new IllegalArgumentException("There is missing information in the product");
		}

		Product product = new Product();
		product.category = command.category();
		product.price = command.price();
		product.brand = brand;
		return product;
	}

	/**
	 * brand와 관계 끊기
	 */
	void disconnectBrand() {
		this.brand = null;
	}

	/**
	 * 상품 정보 업데이트
	 * @param command 업데이트할 데이터
	 */
	public void update(UpsertBrandCommand.Product command) {
		if (Objects.isNull(command.category()) || Objects.isNull(command.price())) {
			throw new IllegalArgumentException("There is missing information in the product");
		}
		this.category = command.category();
		this.price = command.price();
	}
}
