package com.muhammadhh.awsshop.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDto {
	private Long id;
	private String url;
	private String type;
}
