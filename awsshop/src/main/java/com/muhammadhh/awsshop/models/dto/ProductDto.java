package com.muhammadhh.awsshop.models.dto;

import java.util.List;

import com.muhammadhh.awsshop.models.Media;

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
    private Integer stock;
    private String sku;
    private String status;
    private Long categoryId;
    private List<MediaDto> media;
}