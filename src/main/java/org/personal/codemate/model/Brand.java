package org.personal.codemate.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	private List<Product> products = new ArrayList<>();
}
