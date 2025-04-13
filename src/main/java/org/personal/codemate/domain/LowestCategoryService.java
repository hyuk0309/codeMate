package org.personal.codemate.domain;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LowestCategoryService {

	private final ProductRepository productRepository;

	@Transactional(readOnly = true)
	public CategoryLowestPriceSummary summarizeLowestPricesByCategory() {
		List<Product> allProducts = productRepository.findAll();

		Map<Category, Product> lowestProductsByCategory = allProducts.stream()
			.collect(Collectors.groupingBy(
				Product::getCategory,
				Collectors.collectingAndThen(
					Collectors.minBy(Comparator.comparing(Product::getPrice)),
					opt -> opt.orElseThrow(() -> new IllegalStateException("No lowest price product found"))
				)));

		Map<Category, CategoryLowestPriceSummary.BrandInfo> sortedBrandInfoMap = lowestProductsByCategory.entrySet().stream()
				.sorted(Comparator.comparing(entry -> entry.getKey().name())) // category 사전순 정렬
				.collect(Collectors.toMap(
					Map.Entry::getKey, // 키 그대로
					entry -> {
						Product product = entry.getValue();
						return new CategoryLowestPriceSummary.BrandInfo(
							product.getBrand().getName(),
							product.getPrice()
						);
					},
					(e1, e2) -> e1, // 병합 정책 (사실 충돌 없음)
					LinkedHashMap::new // 순서 유지
				));

		int totalPrice = sortedBrandInfoMap.values().stream()
			.map(CategoryLowestPriceSummary.BrandInfo::price)
			.mapToInt(Integer::intValue)
			.sum();

		return new CategoryLowestPriceSummary(sortedBrandInfoMap, totalPrice);
	}
}
