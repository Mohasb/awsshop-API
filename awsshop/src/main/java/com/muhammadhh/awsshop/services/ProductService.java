package com.muhammadhh.awsshop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.muhammadhh.awsshop.models.Product;
import com.muhammadhh.awsshop.respositories.ProductRepository;
import com.muhammadhh.awsshop.utils.apiresponses.OkResponse;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElse(null);
	}
	
	public ResponseEntity<?> saveProduct(Product product) {
		try {
			Product savedProduct = productRepository.save(product);
            OkResponse<Product> okResponse = new OkResponse<>("success", savedProduct, "Product added successfully");
            return new ResponseEntity<>(okResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(productRepository.save(product), HttpStatus.BAD_REQUEST);
		}

	}
	
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}
