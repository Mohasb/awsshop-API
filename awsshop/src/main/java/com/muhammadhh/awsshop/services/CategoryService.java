package com.muhammadhh.awsshop.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<CategoryDto> findAll() {
    	 // Obtener todas las categorías
        List<Category> categories = categoryRepository.findAll();

        // Mapear todas las categorías a CategoryDto
        List<CategoryDto> allCategoryDtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        // Crear un mapa para acceder a las categorías por su ID
        Map<Long, CategoryDto> categoryMap = new HashMap<>();
        for (CategoryDto dto : allCategoryDtos) {
            categoryMap.put(dto.getId(), dto);
        }

        // Crear una lista para las categorías raíz
        List<CategoryDto> rootCategories = new ArrayList<>();

        // Construir la jerarquía
        for (CategoryDto dto : allCategoryDtos) {
            if (dto.getParentCategory() == null) {
                // Si no tiene categoría padre, es una categoría raíz
                rootCategories.add(dto);
            } else {
                // Si tiene categoría padre, añadirlo a la lista de subcategorías
                CategoryDto parentDto = categoryMap.get(dto.getParentCategory().getId());
                if (parentDto != null) {
                    if (parentDto.getSubCategories() == null) {
                        parentDto.setSubCategories(new ArrayList<>());
                    }
                    parentDto.getSubCategories().add(dto);
                }
            }
        }

        // Ordenar las categorías raíz y subcategorías por nombre
        //sortCategories(rootCategories);

        return rootCategories;
    }

    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryDto saveCategory(CategoryDto categoryDto) {
    	
    	Optional<Category> existingCategoryOptional = categoryRepository.findFirstByName(categoryDto.getName());
        
        if (existingCategoryOptional.isPresent()) {
            throw new RuntimeException("Category with the same name already exists");
        }
        
        
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
    @Transactional
    public void deleteById(Long id) {
    	Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            categoryRepository.delete(categoryOptional.get());
        } else {
            throw new RuntimeException("Category not found");
        }
    }
}