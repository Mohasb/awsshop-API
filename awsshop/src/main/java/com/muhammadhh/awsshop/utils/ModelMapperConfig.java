package com.muhammadhh.awsshop.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.muhammadhh.awsshop.models.Product;
import com.muhammadhh.awsshop.models.dto.ProductDto;


@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
    	ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        
        // To ignore id field
        modelMapper.typeMap(ProductDto.class, Product.class).addMappings(mapper -> {
            mapper.skip(Product::setId);
        });
        
     // Configurar mapeo para Product a ProductDto
        modelMapper.addMappings(new PropertyMap<Product, ProductDto>() {
            @Override
            protected void configure() {
                map().setCategoryId(source.getCategory().getId());
            }
        });

        // Configurar mapeo para ProductDto a Product
        modelMapper.addMappings(new PropertyMap<ProductDto, Product>() {
            @Override
            protected void configure() {
                // Para esta conversión,  hacer un lookup manual para Category

            }
        });
        
        return modelMapper;
    }
}