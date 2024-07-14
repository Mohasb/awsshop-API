package com.muhammadhh.awsshop.models;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@NotBlank(message = "name cannot be empty")
	@Size(min = 2, max = 50, message = "name has to be between 2-50 letters")
	private String name;

	@NotNull(message = "price cannot be empty")
	@Positive(message = "Price has to be greater than 0")
	@NumberFormat
	private Double price;

	@Column(columnDefinition = "TEXT")
	@NotBlank(message = "description cannot be empty")
	@Size(min = 2, max = 100, message = "description has to be between 2-100 letters")
	private String description;

	@NotBlank(message = "glbUrl cannot be empty")
	@Size(min = 2, max = 50)
	private String glbUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGlbUrl() {
		return glbUrl;
	}

	public void setGlbUrl(String glbUrl) {
		this.glbUrl = glbUrl;
	}

}
