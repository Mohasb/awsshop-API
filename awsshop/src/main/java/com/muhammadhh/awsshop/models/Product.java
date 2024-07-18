package com.muhammadhh.awsshop.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "name is mandatory")
	@Size(min = 2, max = 50, message = "name has to be between 2-50 letters")
	private String name;

	@NotNull(message = "price is mandatory")
	@Positive(message = "Price has to be greater than 0")
	private Double price;

	@Column(columnDefinition = "TEXT")
	@NotBlank(message = "description cannot be empty")
	@Size(min = 2, max = 100, message = "description has to be between 2-100 letters")
	private String description;

	@NotBlank(message = "glbUrl cannot be empty")
	@Size(min = 2, max = 50)
	private String imageOrGlbUrl;

}
