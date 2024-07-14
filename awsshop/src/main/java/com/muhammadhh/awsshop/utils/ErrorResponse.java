package com.muhammadhh.awsshop.utils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	private String status;
	private String message;
    private Map<String, Object> details;
    
    // Default generic error response
    public ErrorResponse(Long id) {
    	this.status = "error";
    	this.message = "Product not found";
    	
    	Map<String, Object> details = new HashMap<>();
    	details.put("id", id);
    	details.put("timestamp", Instant.now().toString());
        details.put("path", "/products/" + id);
        details.put("error_code", HttpStatus.NOT_FOUND.value());
        
    	this.details = details;
    }
    
    // Custom error response
	public ErrorResponse(String status, String message, Map<String, Object> details) {
		super();
		this.status = status;
		this.message = message;
		this.details = details;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Object> getDetails() {
		return details;
	}
	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}
}
