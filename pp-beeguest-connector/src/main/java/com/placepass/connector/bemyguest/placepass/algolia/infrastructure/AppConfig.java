package com.placepass.connector.bemyguest.placepass.algolia.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.placepass.utils.vendorproduct.ProductHashGenerator;

@Configuration
public class AppConfig {

	@Value("${productid.hash.multiplier}")
	private int hashMultiplier;

	@Value("${productid.hash.adder}")
	private int hashAdder;

	@Bean(name = "ProductHashGenerator")
	public ProductHashGenerator getProductHashGenerator() {
		return ProductHashGenerator.getInstance(hashMultiplier, hashAdder);
	}

}
