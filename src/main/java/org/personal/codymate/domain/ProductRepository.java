package org.personal.codymate.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p FROM product p JOIN FETCH p.brand")
	List<Product> findAllWithBrand();

	@Query("SELECT p FROM product p JOIN FETCH p.brand WHERE p.category = :category")
	List<Product> finddByCategoryWithBrand(Category category);
}
