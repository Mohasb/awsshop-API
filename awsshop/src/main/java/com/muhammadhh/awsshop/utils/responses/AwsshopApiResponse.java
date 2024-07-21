package com.muhammadhh.awsshop.utils.responses;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AwsshopApiResponse<T> {

	private String status;
    
	private String message;
    
    private Map<String, Object> details;
        
	private T data;
}
