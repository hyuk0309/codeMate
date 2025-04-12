package org.personal.codemate.model;

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
@ToString
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
}
