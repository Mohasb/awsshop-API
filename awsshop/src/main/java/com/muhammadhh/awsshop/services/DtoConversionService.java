package com.muhammadhh.awsshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.muhammadhh.awsshop.models.Category;
import com.muhammadhh.awsshop.models.dto.CategoryDto;

public class DtoConversionService {
	 @Autowired
	    private ModelMapper modelMapper;

	    public CategoryDto convertToCategoryDto(Category category) {
	        return modelMapper.map(category, CategoryDto.class);
	    }

	    public Category convertToCategoryEntity(CategoryDto categoryDto) {
	        return modelMapper.map(categoryDto, Category.class);
	    }

		/*
		 * public ProductDto convertToProductDto(Product product) { return
		 * modelMapper.map(product, ProductDto.class); }
		 * 
		 * public Product convertToProductEntity(ProductDto productDto) { return
		 * modelMapper.map(productDto, Product.class); }
		 */
}
