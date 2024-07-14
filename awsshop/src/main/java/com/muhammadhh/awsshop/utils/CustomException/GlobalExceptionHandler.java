package com.muhammadhh.awsshop.utils.CustomException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.muhammadhh.awsshop.utils.apiresponses.ErrorResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            validationErrorResponse.addError(violation.getPropertyPath().toString(), violation.getMessage());
        }
        
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("errors", validationErrorResponse.getErrors());
        errorDetails.put("timestamp", Instant.now().toString());
        errorDetails.put("path", request.getDescription(false).replace("uri=", ""));
        errorDetails.put("error_code", HttpStatus.BAD_REQUEST.value());

        ErrorResponse errorResponse = new ErrorResponse(
            "error",
            "Validation field error",
            errorDetails
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrorResponse.addError(error.getField(), error.getDefaultMessage());
        }
        
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("errors", validationErrorResponse.getErrors());
        errorDetails.put("timestamp", Instant.now().toString());
        errorDetails.put("path", request.getDescription(false).replace("uri=", ""));
        errorDetails.put("error_code", HttpStatus.BAD_REQUEST.value());

        ErrorResponse errorResponse = new ErrorResponse(
            "error",
            "Errores de validación",
            errorDetails
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}