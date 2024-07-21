package com.muhammadhh.awsshop.models.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muhammadhh.awsshop.models.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    
    @NotBlank(message = "name is mandatory")
    @Size(min = 2, max = 50, message = "name has to be between 2-50 letters")
    private String name;
    private CategoryDto parentCategory;
    private List<CategoryDto> subCategories;
}