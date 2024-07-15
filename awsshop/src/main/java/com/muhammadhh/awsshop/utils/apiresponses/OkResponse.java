package com.muhammadhh.awsshop.utils.apiresponses;

import com.muhammadhh.awsshop.models.Product;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response structure for successful operations")
public class OkResponse<T> {
	
    @Schema(description = "Status of the response", example = "success")
	private String status;
    
    @Schema(description = "Data of the response", oneOf = { Product.class })
	private T data;
    
    @Schema(description = "Message of the response", example = "Product added successfully")
	private String message;
	
	public OkResponse(String status, String message,  T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
