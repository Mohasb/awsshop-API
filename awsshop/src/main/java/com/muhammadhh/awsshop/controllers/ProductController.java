package com.muhammadhh.awsshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.muhammadhh.awsshop.models.dto.ProductDto;
import com.muhammadhh.awsshop.services.product.ProductService;
import com.muhammadhh.awsshop.utils.responses.AwsshopApiResponse;
import com.muhammadhh.awsshop.utils.responses.ProductResponseOpenApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Products endpoints crud")
public class ProductController {

	@Autowired
	private ProductService productService;

	// --------------------------------------------------------GETALL---------------------------------------------------------------------------
	@GetMapping
	@Operation(summary = "Retrieve all products", description = "Get a product array with all products")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "A list with all the products", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseOpenApi.class)) }),
			@ApiResponse(responseCode = "400", description = "No products found", content = @Content),
			@ApiResponse(responseCode = "500", content = @Content) })
	public ResponseEntity<AwsshopApiResponse<List<ProductDto>>> getAllProducts() {

		return productService.getAllProducts();

	}

	// --------------------------------------------------------GET(ID)---------------------------------------------------------------------------
	@GetMapping("/{id}")
	@Operation(summary = "Retrieve a product by Id", description = "Get a product object by specifying its id. The response is product object with his fields")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Product not found with that id", content = @Content),
			@ApiResponse(responseCode = "500", content = @Content) })
	public ResponseEntity<?> getProductById(@PathVariable(value = "id", required = true) Long id) {

		return productService.getProductById(id);

	}

	// --------------------------------------------------------POST---------------------------------------------------------------------------
	@PostMapping
	@Operation(summary = "Add a product", description = "add a product")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Product added", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AwsshopApiResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Product not added", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = AwsshopApiResponse.class)) }),
			@ApiResponse(responseCode = "500", content = @Content) })
	public ResponseEntity<AwsshopApiResponse<ProductDto>> createProduct(@Valid @RequestBody ProductDto productDto) {
		return productService.saveProduct(productDto);
	}

	// --------------------------------------------------------PUT---------------------------------------------------------------------------
	@PutMapping("/{id}")
	@Operation(summary = "Put a product", description = "put a product")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "product added", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Product not added", content = @Content) })
	public ResponseEntity<?> updateProduct(@PathVariable(value = "id", required = true) Long id,
			@RequestBody @Valid ProductDto productDetails) {

		return productService.putProduct(id, productDetails);

	}

	// --------------------------------------------------------DELETE---------------------------------------------------------------------------
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a product", description = "delete a product")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "product deleted", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Product not added", content = @Content) })
	public ResponseEntity<?> deleteProduct(@PathVariable(value = "id", required = true) Long id) {

		return productService.deleteProduct(id);
	}
}
