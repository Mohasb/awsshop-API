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

import com.muhammadhh.awsshop.models.dto.CategoryDto;
import com.muhammadhh.awsshop.services.category.CategoryService;
import com.muhammadhh.awsshop.utils.responses.AwsshopApiResponse;
import com.muhammadhh.awsshop.utils.responses.CategoryResponseOpenApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Categories endpoints crud")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// --------------------------------------------------------GETALL---------------------------------------------------------------------------
	@GetMapping
	@Operation(summary = "Retrieve all categories hierarchy", description = "Get all categories in hierarchy format")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A list with all the categories", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CategoryResponseOpenApi.class))) }),
			@ApiResponse(responseCode = "400", description = "No categories found", content = @Content),
			@ApiResponse(responseCode = "500", content = @Content) })
	public ResponseEntity<AwsshopApiResponse<List<CategoryDto>>> getAllCategories() {

		return categoryService.findAll();

	}

	// --------------------------------------------------------GET(ID)---------------------------------------------------------------------------
	@GetMapping("/{id}")
	@Operation(summary = "Retrieve a category by Id", description = "Get a category object by specifying its id. The response is category object with his subcategories")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "category found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseOpenApi.class)) }),
			@ApiResponse(responseCode = "400", description = "category not found with that id", content = @Content),
			@ApiResponse(responseCode = "500", content = @Content) })
	public ResponseEntity<AwsshopApiResponse<CategoryDto>> getCategoryById(
			@PathVariable(value = "id", required = true) Long id) {

		return categoryService.findById(id);

	}

	// --------------------------------------------------------POST---------------------------------------------------------------------------
	@PostMapping
	@Operation(summary = "Add a category", description = "add a category")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Category added", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseOpenApi.class)) }),
			@ApiResponse(responseCode = "400", description = "Category not added", content = @Content),
			@ApiResponse(responseCode = "500", content = @Content) })
	public ResponseEntity<AwsshopApiResponse<CategoryDto>> createCategory(@RequestBody CategoryDto categoryDto) {

		return categoryService.saveCategory(categoryDto);

	}

	// --------------------------------------------------------PUT---------------------------------------------------------------------------
	@PutMapping("/{id}")
	@Operation(summary = "Put a category", description = "put a category")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "category modified", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseOpenApi.class)) }),
			@ApiResponse(responseCode = "400", description = "category not modified", content = @Content),
			@ApiResponse(responseCode = "500", content = @Content) })
	public ResponseEntity<AwsshopApiResponse<CategoryDto>> putCategory(@PathVariable(value = "id", required = true) Long id, @RequestBody CategoryDto categoryDto) {

		return categoryService.putCategory(id, categoryDto);

	}

	// --------------------------------------------------------DELETE---------------------------------------------------------------------------
	@DeleteMapping("/{id}")
	@Operation(summary = "Add a category", description = "add a category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "category added", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class)) }),
			@ApiResponse(responseCode = "400", description = "category not added", content = @Content),
			@ApiResponse(responseCode = "500", content = @Content) })
	public ResponseEntity<AwsshopApiResponse<CategoryDto>> deleteCategory(
			@PathVariable(value = "id", required = true) Long id) {

		return categoryService.deleteById(id);

	}
}