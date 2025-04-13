package org.personal.codemate.domain;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {

	private final BrandRepository brandRepository;

	@Transactional
	public Brand upsertBrand(UpsertBrandCommand command) {
		if (Objects.isNull(command.id())) {
			return createNewBrand(command);
		} else {
			return updateExistsBrand(command);
		}
	}

	private Brand updateExistsBrand(UpsertBrandCommand command) {
		Brand brand = brandRepository.findById(command.id()).orElseThrow(() -> new IllegalArgumentException("invalid brand id: " + command.id()));
		brand.update(command);
		return brand;
	}

	private Brand createNewBrand(UpsertBrandCommand command) {
		Brand newBrand = Brand.createNewBrand(command);
		return brandRepository.save(newBrand);
	}

}
