package com.muhammadhh.awsshop.utils.responses;

import java.util.List;
import java.util.Map;

import com.muhammadhh.awsshop.models.dto.CategoryDto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseOpenApi {
	@Schema(description = "Status of the response", example = "SUCCESS")
	private String status;

	@Schema(description = "Message describing the response", example = "List of all categories")
	private String message;

	@Schema(description = "Additional details about the response", example = "{}")
	private Map<String, Object> details;

	@ArraySchema(schema = @Schema(implementation = CategoryDto.class))
	private List<CategoryDto> data;

}
