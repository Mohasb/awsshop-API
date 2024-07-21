package com.muhammadhh.awsshop.utils;

public class ApiConstants {
	 // Statuses
    public static final String SUCCESS_STATUS = "SUCCESS";
    public static final String ERROR_STATUS = "ERROR";
    
    // Messages Category
    public static final String CATEGORY_LIST_SUCCESS_MESSAGE = "List of all categories";
    public static final String CATEGORY_FOUND_MESSAGE = "Category found";
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found";
    public static final String NO_CATEGORIES_MESSAGE = "There are no categories";
    public static final String CATEGORY_UPDATED_SUCCESS_MESSAGE = "Category updated successfully";
    public static final String CATEGORY_UPDATE_ERROR_MESSAGE = "An error occurred while updating the category";
    public static final String CATEGORY_CREATE_ERROR_MESSAGE = "An error occurred while creating the category";
    public static final String CATEGORY_DELETE_ERROR_MESSAGE = "An error occurred while deleting the category";
    public static final String CATEGORY_CREATE_SUCCESS = "Category created successfully";
    public static final String CATEGORY_CREATE_ERROR_DUPLICATED = "Category with the same name already exists";
    public static final String CATEGORY_DELETE_SUCESS = "Category deleted successfully";

    
    
    // General messages
    public static final String SERVER_ERROR = "Internal server error";
    public static final String GENERAL_ERROR_MESSAGE = "An error occurred";
    public static final String UPDATE_SUCCESS_MESSAGE = "Updated succefully";
	public static final String UPDATE_ERROR_MESSAGE = "Error on update";
	public static final String EXCEPTION = "exception";

    // HTTP Status Codes
    public static final int HTTP_STATUS_OK = 200;
    public static final int HTTP_STATUS_NO_CONTENT = 204;
    public static final int HTTP_STATUS_NOT_FOUND = 404;
    public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;
	
    
    // Private constructor to prevent instantiation
    private ApiConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    
}
