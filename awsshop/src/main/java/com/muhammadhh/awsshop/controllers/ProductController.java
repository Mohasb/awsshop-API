package com.muhammadhh.awsshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhammadhh.awsshop.models.Product;
import com.muhammadhh.awsshop.services.ProductService;
import com.muhammadhh.awsshop.utils.ApiResponse;
import com.muhammadhh.awsshop.utils.ErrorResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Products endpoints crud")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable(value = "id", required = true) Long id) {
		
		Product product = productService.getProductById(id);

		if (product != null) {
			ApiResponse<Product> response = new ApiResponse<Product>("success", product, null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
	        ErrorResponse errorResponse = new ErrorResponse(id);
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public Product createProduct(@RequestBody Product product) {
		return productService.saveProduct(product);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id", required = true) Long id, @RequestBody Product productDetails) {
		Product product = productService.getProductById(id);

		if (product != null) {
			product.setName(productDetails.getName());
			product.setPrice(productDetails.getPrice());
			product.setDescription(productDetails.getDescription());
			Product updatedProduct = productService.saveProduct(product);
			return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable(value = "id", required = true) Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
