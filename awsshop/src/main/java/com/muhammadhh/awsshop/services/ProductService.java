package com.muhammadhh.awsshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.muhammadhh.awsshop.models.Product;
import com.muhammadhh.awsshop.respositories.ProductRepository;
import com.muhammadhh.awsshop.utils.apiresponses.ErrorResponse;
import com.muhammadhh.awsshop.utils.apiresponses.OkResponse;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public ResponseEntity<?> getAllProducts() {

		List<Product> productsList = productRepository.findAll();

		if (!productsList.isEmpty()) {
			return new ResponseEntity<>(new OkResponse<>("SUCCESS", "List of all products", productsList),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new OkResponse<>("SUCCESS", "There are no products", productsList),
					HttpStatus.OK);
		}
	}

	public ResponseEntity<?> getProductById(Long id) {

		Product product = productRepository.findById(id).orElse(null);
		if (product != null)
			return new ResponseEntity<>(new OkResponse<>("SUCCESS", "Product found", product), HttpStatus.CREATED);
		else
			return new ResponseEntity<>(
					new ErrorResponse("ERROR", "Product with id=" + id.toString() + " not found", null),
					HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> saveProduct(Product product) {

		Product savedProduct = productRepository.save(product);
		return new ResponseEntity<>(new OkResponse<>("SUCCESS", "Product added successfully", savedProduct),
				HttpStatus.CREATED);
	}

	public ResponseEntity<?> deleteProduct(Long id) {
		try {
			Optional<Product> productOptional = productRepository.findById(id);
			if (productOptional.isPresent()) {
				productRepository.deleteById(id);
				return new ResponseEntity<>(
						new OkResponse<>("SUCCESS", "Product deleted successfully", productOptional), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new ErrorResponse("ERROR", "Product with id=" + id.toString() + " not found", null),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponse("ERROR", "Error occurred while deleting product", null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
