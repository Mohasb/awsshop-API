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
import com.muhammadhh.awsshop.services.ProductService;
import com.muhammadhh.awsshop.utils.AwsshopApiResponse;

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
//SWAGGER
@Tag(name = "Products", description = "Products endpoints crud")
public class ProductController {

	@Autowired
	private ProductService productService;

	// --------------------------------------------------------GETALL---------------------------------------------------------------------------
	@GetMapping
	@Operation(summary = "Retrieve all products", description = "Get a product array with all products")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "A list with all the products", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class))) }),
			@ApiResponse(responseCode = "400", description = "No products found", content = @Content),
			@ApiResponse(responseCode = "500", content = @Content) })
	public ResponseEntity<AwsshopApiResponse<List<Product>>> getAllProducts() {
		return productService.getAllProducts();
	}

	// --------------------------------------------------------GET(ID)---------------------------------------------------------------------------
	@Operation(summary = "Retrieve a product by Id", description = "Get a product object by specifying its id. The response is product object with his fields")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto encontrado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Product not found with that id", content = @Content),
			@ApiResponse(responseCode = "500", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable(value = "id", required = true) Long id) {

		return productService.getProductById(id);
	}

	// --------------------------------------------------------POST---------------------------------------------------------------------------
	@PostMapping
	@Operation(summary = "Add a product", description = "add a product")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Product added", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = AwsshopApiResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Product not added", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = AwsshopApiResponse.class)) }),
			@ApiResponse(responseCode = "500", content = @Content) })
	public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
		return productService.saveProduct(product);
	}

	// --------------------------------------------------------PUT---------------------------------------------------------------------------
	@PutMapping("/{id}")
	@Operation(summary = "Put a product", description = "put a product")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "product added", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Product not added", content = @Content) })
	public ResponseEntity<?> updateProduct(@PathVariable(value = "id", required = true) Long id,
			@RequestBody @Valid Product productDetails) {
		
		return  productService.getProductById(id);
		
	}

	// --------------------------------------------------------DELETE---------------------------------------------------------------------------
	@DeleteMapping("/{id}")
	@Operation(summary = "Add a product", description = "add a product")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "product added", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Product not added", content = @Content) })
	public ResponseEntity<?> deleteProduct(@PathVariable(value = "id", required = true) Long id) {
		
		return productService.deleteProduct(id);
	}
}
