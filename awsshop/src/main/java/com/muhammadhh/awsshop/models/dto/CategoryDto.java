package com.muhammadhh.awsshop.models.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muhammadhh.awsshop.models.Category;

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
    private String name;
    private Category parentCategory;
    private List<CategoryDto> subCategories;
}