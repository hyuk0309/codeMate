package org.personal.codemate.domain;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {

	private final ApplicationEventPublisher eventPublisher;
	private final BrandRepository brandRepository;

	@Transactional
	public Brand upsertBrand(UpsertBrandCommand command) {
		Brand brand;
		if (Objects.isNull(command.id())) {
			brand = createNewBrand(command);
		} else {
			brand = updateExistsBrand(command);
		}
		eventPublisher.publishEvent(new BrandChangeEvent());
		return brand;
	}

	private Brand updateExistsBrand(UpsertBrandCommand command) {
		Brand brand = brandRepository.findById(command.id()).orElseThrow(() -> new InvalidRequestException("invalid brand id: " + command.id()));
		brand.update(command);
		brandRepository.save(brand);
		return brand;
	}

	private Brand createNewBrand(UpsertBrandCommand command) {
		Brand newBrand = Brand.createNewBrand(command);
		return brandRepository.save(newBrand);
	}

}
