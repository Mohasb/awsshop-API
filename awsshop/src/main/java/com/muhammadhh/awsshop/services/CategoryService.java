package com.muhammadhh.awsshop.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muhammadhh.awsshop.models.Category;
import com.muhammadhh.awsshop.models.dto.CategoryDto;
import com.muhammadhh.awsshop.respositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryDto saveCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        
        if (categoryDto.getParentCategory() != null) {
            Optional<Category> parentCategoryOptional = categoryRepository.findById(categoryDto.getParentCategory().getId());
            if (parentCategoryOptional.isPresent()) {
                category.setParentCategory(parentCategoryOptional.get());
            } else {
                throw new RuntimeException("Parent category not found");
            }
        }
        
        
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}