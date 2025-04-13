package org.personal.codemate.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Getter
@ToString
@Entity(name = "brand")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // for JPA
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Product> products = new ArrayList<>();

	/**
	 * 새로운 브랜드를 생성한다.
	 *
	 * @param command 새로운 브랜드 정보
	 * @return 새로운 브랜드
	 */
	static Brand createNewBrand(UpsertBrandCommand command) {

		// validate brand info
		if (Objects.nonNull(command.id()) || !StringUtils.hasText(command.name())) {
			throw new IllegalArgumentException("the name of the brand cannot be empty");
		}
		Brand newBrand = new Brand();
		newBrand.name = command.name();

		// validate products info
		List<UpsertBrandCommand.Product> brandProducts = command.products();
		if (CollectionUtils.isEmpty(brandProducts)) {
			throw new IllegalArgumentException("product list is empty");
		}

		List<Product> newProducts = brandProducts.stream().map(bp -> Product.newProduct(bp, newBrand)).toList();
		Set<Category> productCategories = newProducts.stream().map(Product::getCategory).collect(Collectors.toSet());

		if (Category.values().length != productCategories.size()) {
			throw new IllegalArgumentException("Each brand must have products in all categories.");
		}

		newBrand.products = newProducts;
		return newBrand;
	}

	/**
	 * 기존 브랜드 정보를 업데이트 한다.
	 * @param command 업데이트할 브랜드 정보
	 */
	public void update(UpsertBrandCommand command) {
	}
}
