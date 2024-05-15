package com.example.ProductManageSample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.ProductManageSample.repository")
public class ProductManageSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductManageSampleApplication.class, args);
	}

}
