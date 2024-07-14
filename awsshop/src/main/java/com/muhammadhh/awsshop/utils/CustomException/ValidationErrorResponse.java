package com.muhammadhh.awsshop.utils.CustomException;

import java.util.HashMap;
import java.util.Map;

public class ValidationErrorResponse {
    private Map<String, String> errors = new HashMap<>();

    public Map<String, String> getErrors() {
        return errors;
    }

    public void addError(String field, String errorMessage) {
        this.errors.put(field, errorMessage);
    }
}