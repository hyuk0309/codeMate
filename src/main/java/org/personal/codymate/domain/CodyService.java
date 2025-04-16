package org.personal.codymate.domain;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.personal.codymate.repository.ProductRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodyService implements CommandLineRunner {

	private final ProductRepository productRepository;

	private LowestPriceSummaryByCategory lowestPriceSummaryByCategory;
	private LowestPriceSummaryByBrand lowestPriceSummaryByBrand;

	@Override
	public void run(String... args) {
		summarizeLowestPricesByCategory();
		summarizeLowestPriceByBrand();
	}

	synchronized void summarizeLowestPricesByCategory() {
		List<Product> allProducts = productRepository.findAllWithBrand();

		Map<Category, Product> lowestProductsByCategory = allProducts.stream()
			.collect(Collectors.groupingBy(
				Product::getCategory,
				Collectors.collectingAndThen(
					Collectors.minBy(Comparator.comparing(Product::getPrice)),
					opt -> opt.orElseThrow(() -> new IllegalStateException("No bottom price product found"))
				)));

		Map<Category, LowestPriceSummaryByCategory.BrandInfo> sortedBrandInfoMap = lowestProductsByCategory.entrySet().stream()
			.sorted(Comparator.comparing(entry -> entry.getKey().name())) // category 사전순 정렬
			.collect(Collectors.toMap(
				Map.Entry::getKey, // 키 그대로
				entry -> {
					Product product = entry.getValue();
					return new LowestPriceSummaryByCategory.BrandInfo(
						product.getBrand().getName(),
						product.getPrice()
					);
				},
				(e1, e2) -> e1, // 병합 정책 (사실 충돌 없음)
				LinkedHashMap::new // 순서 유지
			));

		int totalPrice = sortedBrandInfoMap.values().stream()
			.map(LowestPriceSummaryByCategory.BrandInfo::price)
			.mapToInt(Integer::intValue)
			.sum();

		this.lowestPriceSummaryByCategory = new LowestPriceSummaryByCategory(sortedBrandInfoMap, totalPrice);
	}

	public LowestPriceSummaryByCategory getLowestPriceSummaryByCategory() {
		return lowestPriceSummaryByCategory;
	}

	void summarizeLowestPriceByBrand() {
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

		List<LowestPriceSummaryByBrand.ProductInfo> productInfos = resultMap.get(cheapestBrandAndPrice.getKey()).entrySet().stream()
			.map(entry -> {
				String category = entry.getKey().name();
				Integer price = entry.getValue().getPrice();
				return new LowestPriceSummaryByBrand.ProductInfo(category, price);
			}).toList();

		this.lowestPriceSummaryByBrand = new LowestPriceSummaryByBrand(cheapestBrandAndPrice.getKey().getName(), productInfos, cheapestBrandAndPrice.getValue());
	}

	public LowestPriceSummaryByBrand getLowestPriceSummaryByBrand() {
		return lowestPriceSummaryByBrand;
	}

	public TopAndBottomPriceProduct findTopAndBottomPriceProducts(Category category) {
		List<Product> products = productRepository.finddByCategoryWithBrand(category);

		TopAndBottomPriceProduct.ProductInfo cheapestProduct = products.stream()
			.min(Comparator.comparing(Product::getPrice))
			.map(product -> new TopAndBottomPriceProduct.ProductInfo(product.getBrand().getName(), product.getPrice()))
			.orElseThrow(IllegalStateException::new);

		TopAndBottomPriceProduct.ProductInfo expensiveProduct = products.stream()
			.max(Comparator.comparing(Product::getPrice))
			.map(product -> new TopAndBottomPriceProduct.ProductInfo(product.getBrand().getName(), product.getPrice()))
			.orElseThrow(() -> new IllegalStateException("No expensive product found"));

		return new TopAndBottomPriceProduct(category.name(), cheapestProduct, expensiveProduct);
	}

	public void handleEvent() {
		summarizeLowestPricesByCategory();
		summarizeLowestPriceByBrand();
	}
}
