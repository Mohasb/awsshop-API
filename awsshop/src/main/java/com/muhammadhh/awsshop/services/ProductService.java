package com.muhammadhh.awsshop.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.muhammadhh.awsshop.models.Category;
import com.muhammadhh.awsshop.models.Product;
import com.muhammadhh.awsshop.models.dto.CategoryDto;
import com.muhammadhh.awsshop.respositories.CategoryRepository;
import com.muhammadhh.awsshop.respositories.ProductRepository;
import com.muhammadhh.awsshop.utils.AwsshopApiResponse;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	public ResponseEntity<AwsshopApiResponse<List<Product>>> getAllProducts() {

		try {
			List<Product> productsList = productRepository.findAll();

			if (!productsList.isEmpty()) {
				return new ResponseEntity<>(
						new AwsshopApiResponse<>("SUCCESS", "List of all products", null, productsList), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new AwsshopApiResponse<>("SUCCESS", "There are no products", null, productsList),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			Map<String, Object> errorDetails = new HashMap<>();
			errorDetails.put("exception", e.getMessage());

			return new ResponseEntity<>(
					new AwsshopApiResponse<>("ERROR", "An error occurred while fetching products", errorDetails, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<AwsshopApiResponse<Product>> getProductById(Long id) {

		try {
			Product product = productRepository.findById(id).orElse(null);
			if (product != null) {
				return new ResponseEntity<>(new AwsshopApiResponse<>("SUCCESS", "Product found", null, product),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new AwsshopApiResponse<>("ERROR", "Product with id=" + id + " not found", null, null),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			Map<String, Object> errorDetails = new HashMap<>();
			errorDetails.put("exception", e.getMessage());

			return new ResponseEntity<>(new AwsshopApiResponse<>("ERROR",
					"An error occurred while fetching the product", errorDetails, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> saveProduct(Product product) {

		try {
			Optional<Category> productCategory = categoryRepository.findById(product.getCategory().getId());

			if (productCategory.isPresent()) {
				product.setCategory(productCategory.get());
			}

			Product savedProduct = productRepository.save(product);
			return new ResponseEntity<>(
					new AwsshopApiResponse<>("SUCCESS", "Product added successfully", null, savedProduct),
					HttpStatus.CREATED);
		} catch (Exception e) {
			Map<String, Object> errorDetails = new HashMap<>();
			errorDetails.put("exception", e.getMessage());

			return new ResponseEntity<>(
					new AwsshopApiResponse<>("ERROR", "An error occurred while saving the product", errorDetails, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> deleteProduct(Long id) {
		try {
			Optional<Product> productOptional = productRepository.findById(id);
			if (productOptional.isPresent()) {
				productRepository.deleteById(id);
				return new ResponseEntity<>(new AwsshopApiResponse<>("SUCCESS", "Product deleted successfully", null,
						productOptional.get()), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new AwsshopApiResponse<>("ERROR", "Product with id=" + id + " not found", null, null),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			Map<String, Object> errorDetails = new HashMap<>();
			errorDetails.put("exception", e.getMessage());

			return new ResponseEntity<>(new AwsshopApiResponse<>("ERROR",
					"An error occurred while deleting the product", errorDetails, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
