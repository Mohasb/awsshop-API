package com.muhammadhh.awsshop.services.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muhammadhh.awsshop.models.Category;
import com.muhammadhh.awsshop.models.dto.CategoryDto;
import com.muhammadhh.awsshop.respositories.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UtilsCategory utilsCategory;

	@Transactional
	public List<CategoryDto> findAll() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().filter(category -> category.getParentCategory() == null)
				.map(utilsCategory::convertToDto).collect(Collectors.toList());
	}

	public CategoryDto findById(Long id) {
		return categoryRepository.findById(id).map(utilsCategory::convertToDto)
				.orElseThrow(() -> new RuntimeException("Category not found"));
	}

	@Transactional
	public CategoryDto saveCategory(CategoryDto categoryDto) {

		Optional<Category> existingCategoryOptional = categoryRepository.findFirstByName(categoryDto.getName());

		if (existingCategoryOptional.isPresent()) {
			throw new RuntimeException("Category with the same name already exists");
		}

		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);

		if (savedCategory.getParentCategory().getName() == null) {
			Optional<Category> parentCategoryOptional = categoryRepository
					.findById(categoryDto.getParentCategory().getId());
			savedCategory.getParentCategory().setName(parentCategoryOptional.get().getName());
		}

		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Transactional
	public CategoryDto deleteById(Long id) {
		Optional<Category> categoryOptional = categoryRepository.findById(id);
		if (categoryOptional.isPresent()) {
			Category category = categoryOptional.get();
			categoryRepository.delete(category);
			return utilsCategory.convertToDto(category);
		} else {
			throw new RuntimeException("Category not found");
		}
	}
}