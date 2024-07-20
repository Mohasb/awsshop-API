package com.muhammadhh.awsshop.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhammadhh.awsshop.models.dto.CategoryDto;
import com.muhammadhh.awsshop.services.CategoryService;
import com.muhammadhh.awsshop.utils.AwsshopApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Categories endpoints crud")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;


	@GetMapping
    public ResponseEntity<AwsshopApiResponse<List<CategoryDto>>> getAllCategories() {
        List<CategoryDto> categories = categoryService.findAll();
        return new ResponseEntity<>(
                new AwsshopApiResponse<>("SUCCESS", "List of all categories", null, categories),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AwsshopApiResponse<CategoryDto>> getCategoryById(@PathVariable Long id) {
        try {
            CategoryDto categoryDto = categoryService.findById(id);
            return new ResponseEntity<>(
                    new AwsshopApiResponse<>("SUCCESS", "Category found", null, categoryDto),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new AwsshopApiResponse<>("ERROR", "Category not found", null, null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping
    public ResponseEntity<AwsshopApiResponse<CategoryDto>> createCategory(@RequestBody CategoryDto categoryDto) {
        try {
            CategoryDto savedCategoryDto = categoryService.saveCategory(categoryDto);
            return new ResponseEntity<>(
                    new AwsshopApiResponse<>("SUCCESS", "Category created successfully", null, savedCategoryDto),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new AwsshopApiResponse<>("ERROR", "An error occurred while creating the category", Map.of("exception", e.getMessage()), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AwsshopApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteById(id);
            return new ResponseEntity<>(
                    new AwsshopApiResponse<>("SUCCESS", "Category deleted successfully", null, null),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new AwsshopApiResponse<>("ERROR", "Category not found", null, null),
                    HttpStatus.NOT_FOUND
            );
        }
    }
}