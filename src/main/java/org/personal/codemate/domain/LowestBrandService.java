package org.personal.codemate.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LowestBrandService implements CommandLineRunner {

	private final ProductRepository productRepository;
	private LowestBrandCody cachedLowestPriceAndBrandSummary;

	public void loadLowestPriceBrandAndProductByCategory() {
		List<Product> allProducts = productRepository.findAllWithBrand();

		Map<Brand, Map<Category, Product>> resultMap = allProducts.stream()
			.collect(Collectors.groupingBy(
				Product::getBrand,
				Collectors.collectingAndThen(
					Collectors.toMap(Product::getCategory, Function.identity(), BinaryOperator.minBy(Comparator.comparingInt(Product::getPrice))),
					innerMap -> innerMap
				)
			));

		Map.Entry<Brand, Integer> cheapestBrandAndPrice = resultMap.entrySet().stream()
			.map(entry -> {
				Brand brand = entry.getKey();
				Map<Category, Product> categoryMap = entry.getValue();
				int totalPrice = categoryMap.values().stream()
					.mapToInt(Product::getPrice)
					.sum();
				return Map.entry(brand, totalPrice);
			})
			.min(Comparator.comparingInt(Map.Entry::getValue))
			.orElseThrow(IllegalStateException::new);

		List<LowestBrandCody.ProductInfo> productInfos = resultMap.get(cheapestBrandAndPrice.getKey()).entrySet().stream()
			.map(entry -> {
				String category = entry.getKey().name();
				Integer price = entry.getValue().getPrice();
				return new LowestBrandCody.ProductInfo(category, price);
			}).toList();

		this.cachedLowestPriceAndBrandSummary = new LowestBrandCody(cheapestBrandAndPrice.getKey().getName(), productInfos, cheapestBrandAndPrice.getValue());
	}

	public LowestBrandCody getLowestPriceAndBrandSummary() {
		return cachedLowestPriceAndBrandSummary;
	}

	@Override
	public void run(String... args) throws Exception {
		loadLowestPriceBrandAndProductByCategory();
	}
}
