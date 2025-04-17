package org.personal.codymate.repository;

import java.util.List;

import org.personal.codymate.domain.Category;
import org.personal.codymate.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p FROM product p JOIN FETCH p.brand")
	List<Product> findAllWithBrand();

	@Query("SELECT p FROM product p JOIN FETCH p.brand WHERE p.category = :category ORDER BY p.price ASC LIMIT 1")
	Product findLowestPriceProduct(Category category);

	@Query("SELECT p FROM product p JOIN FETCH p.brand WHERE p.category = :category ORDER BY p.price DESC LIMIT 1")
	Product findHighestPriceProduct(Category category);
}
