package com.srijan.eapp.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.srijan.eapp.model.Product;


class ProductServiceTest {
	
	private ProductService productService;
	
	@BeforeEach
	void setup() {
		productService = new ProductService();
	}

	@Test
	void createProductTest() throws ClassNotFoundException, SQLException {
		Product product = new Product();
		product.setModelYear(2020);
		product.setPrice(99000);
		product.setProductName("I Phone");
		boolean flag = productService.createProduct(product);
		assertTrue(flag);
	}
	
	@Test
	void getProductsTest() throws ClassNotFoundException, SQLException {
		List<Product> prods = productService.getAllProducts();
		assertTrue(prods.size() > 0);
	}

}
