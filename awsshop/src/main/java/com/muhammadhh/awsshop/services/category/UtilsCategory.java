package com.muhammadhh.awsshop.services.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.muhammadhh.awsshop.models.Category;
import com.muhammadhh.awsshop.models.dto.CategoryDto;

@Component
public class UtilsCategory {
	public CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setParentCategory(category.getParentCategory() != null ? convertToSimpleDto(category.getParentCategory()) : null);

        List<CategoryDto> subCategoryDtos = category.getSubCategories().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        categoryDto.setSubCategories(subCategoryDtos);

        return categoryDto;
    }
    
    private CategoryDto convertToSimpleDto(Category category) {
        CategoryDto simpleDto = new CategoryDto();
        simpleDto.setId(category.getId());
        simpleDto.setName(category.getName());
        return simpleDto;
    }
}
