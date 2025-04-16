package org.personal.codymate.domain;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.personal.codymate.repository.BrandRepository;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

/**
 * 브랜드와 포함된 상품 정보를 변경하는 서비스입니다.
 */
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
