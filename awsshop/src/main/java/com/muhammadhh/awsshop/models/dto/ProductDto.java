package com.muhammadhh.awsshop.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String imageOrGlbUrl;
    private Integer stock;
    private String sku;
    private String status;
    private Long categoryId;
}