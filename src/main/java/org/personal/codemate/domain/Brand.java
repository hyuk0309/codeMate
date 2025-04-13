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

		List<Product> newProducts = brandProducts.stream().map(bp -> Product.newProduct(bp, newBrand)).collect(Collectors.toList());
		validateProducts(newProducts);

		newBrand.products = newProducts;
		return newBrand;
	}

	/**
	 * 기존 브랜드 정보를 업데이트 한다.
	 * @param command 업데이트할 브랜드 정보
	 */
	public void update(UpsertBrandCommand command) {
		// brand info update
		if (!StringUtils.hasText(command.name())) {
			throw new IllegalArgumentException("the name of the brand cannot be empty");
		}
		this.name = command.name();

		// product info update
		// 2. product 데이터 비교 및 아이디 같으면 update & 없는 id는 삭제 & 새로운 데이터 추가
		Map<Long, UpsertBrandCommand.Product> productsToUpdate = command.products().stream()
			.filter(p -> Objects.nonNull(p.id()))
			.collect(Collectors.toMap(UpsertBrandCommand.Product::id, p -> p));

		List<UpsertBrandCommand.Product> newProductToAdd = command.products().stream()
			.filter(p -> Objects.isNull(p.id()))
			.toList();

		// delete products
		this.getProducts().removeIf(p -> {
			boolean isDelete = !productsToUpdate.containsKey(p.getId());
			if (isDelete) {
				p.disconnectBrand();
			}
			return isDelete;
		});

		// update products
		if (this.products.size() != productsToUpdate.size()) {
			throw new IllegalArgumentException("The products to be updated do not match the existing products");
		}
		for (Product product : this.products) {
			if (productsToUpdate.containsKey(product.getId())) {
				product.update(productsToUpdate.get(product.getId()));
			}
		}

		// add new Product
		List<Product> newProducts = newProductToAdd.stream()
			.map(newProduct -> Product.newProduct(newProduct, this))
			.toList();
		this.products.addAll(newProducts);

		validateProducts(this.products);
	}

	/**
	 * 브랜드의 상품들이 올바르게 세팅되었는지 검증한다.
	 * @param products 상품들
	 */
	static private void validateProducts(List<Product> products) {
		if (CollectionUtils.isEmpty(products)) {
			throw new IllegalArgumentException("product list is empty");
		}
		Set<Category> productCategories = products.stream().map(Product::getCategory).collect(Collectors.toSet());

		if (Category.values().length != productCategories.size()) {
			throw new IllegalArgumentException("Each brand must have products in all categories.");
		}
	}
}
