package com.muhammadhh.awsshop.services.category;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.muhammadhh.awsshop.models.Category;
import com.muhammadhh.awsshop.models.dto.CategoryDto;
import com.muhammadhh.awsshop.respositories.CategoryRepository;
import com.muhammadhh.awsshop.utils.ApiConstants;
import com.muhammadhh.awsshop.utils.responses.AwsshopApiResponse;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UtilsCategory utilsCategory;

	// --------------------------------------------------------GETALL---------------------------------------------------------------------------
	@Transactional
	public ResponseEntity<AwsshopApiResponse<List<CategoryDto>>> findAll() {
		try {
			List<Category> categories = categoryRepository.findAll();
			List<CategoryDto> categoryHierarchy = categories.stream()
					.filter(category -> category.getParentCategory() == null).map(utilsCategory::convertToDto)
					.collect(Collectors.toList());

			String message = !categoryHierarchy.isEmpty() ? ApiConstants.CATEGORY_LIST_SUCCESS_MESSAGE : ApiConstants.NO_CATEGORIES_MESSAGE;
			HttpStatus httpStatus = !categoryHierarchy.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;

			return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.SUCCESS_STATUS, message, null, categoryHierarchy),
					httpStatus);
		} catch (Exception e) {
			return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS,
					ApiConstants.SERVER_ERROR, Map.of(ApiConstants.EXCEPTION, e.getMessage()), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// --------------------------------------------------------GET(ID)---------------------------------------------------------------------------
	public ResponseEntity<AwsshopApiResponse<CategoryDto>> findById(Long id) {
		try {
			Optional<Category> categoryOptional = categoryRepository.findById(id);

			if (categoryOptional.isPresent()) {
				CategoryDto categoryDto = utilsCategory.convertToDto(categoryOptional.get());
				return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.SUCCESS_STATUS, ApiConstants.CATEGORY_FOUND_MESSAGE, null, categoryDto),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS, ApiConstants.CATEGORY_NOT_FOUND_MESSAGE, null, null),
						HttpStatus.NOT_FOUND);
			}

		} catch (RuntimeException e) {
			return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS,
					ApiConstants.SERVER_ERROR, Map.of(ApiConstants.EXCEPTION, e.getMessage()), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// --------------------------------------------------------POST---------------------------------------------------------------------------
	@Transactional
	public ResponseEntity<AwsshopApiResponse<CategoryDto>> saveCategory(CategoryDto categoryDto) {
		try {
			Optional<Category> existingCategoryOptional = categoryRepository.findFirstByName(categoryDto.getName());

			if (!existingCategoryOptional.isPresent()) {
				Category category = modelMapper.map(categoryDto, Category.class);

				if (category.getParentCategory() != null) {
					Optional<Category> parentCategoryOptional = categoryRepository
							.findById(categoryDto.getParentCategory().getId());
					if (!parentCategoryOptional.isPresent()) {
						return new ResponseEntity<>(
								new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS, ApiConstants.PARENT_CATEGORY_NOT_FOUND_MESSAGE, null, null),
								HttpStatus.NOT_FOUND);
					}
					
					category.getParentCategory().setName(parentCategoryOptional.get().getName());
				}
				
				Category savedCategory = categoryRepository.save(category);
				CategoryDto savedCategoryDto = modelMapper.map(savedCategory, CategoryDto.class);

				return new ResponseEntity<>(
						new AwsshopApiResponse<>(ApiConstants.SUCCESS_STATUS, ApiConstants.CATEGORY_CREATE_SUCCESS, null, savedCategoryDto),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(
						new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS, ApiConstants.CATEGORY_CREATE_ERROR_DUPLICATED, null, null),
						HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS,
					ApiConstants.CATEGORY_CREATE_ERROR_MESSAGE, Map.of(ApiConstants.EXCEPTION, e.getMessage()), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// --------------------------------------------------------PUT---------------------------------------------------------------------------
	public ResponseEntity<AwsshopApiResponse<CategoryDto>> putCategory(Long id, CategoryDto categoryDto) {
		try {
			Optional<Category> existingCategoryOptional = categoryRepository.findById(id);

			if (existingCategoryOptional.isPresent()) {
	            Category existingCategory = existingCategoryOptional.get();
          
				// Update the existing category with the new data
				existingCategory.setName(categoryDto.getName());
				Category parentCategory = null;
	            if (categoryDto.getParentCategory() != null) {
	                parentCategory = categoryRepository.findById(categoryDto.getParentCategory().getId())
	                        .orElse(null);
	            }
	            existingCategory.setParentCategory(parentCategory);

				Category updatedCategory = categoryRepository.save(existingCategory);
				CategoryDto updatedCategoryDto = utilsCategory.convertToDto(updatedCategory);

				return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.SUCCESS_STATUS,
						ApiConstants.UPDATE_SUCCESS_MESSAGE, null, updatedCategoryDto), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS, ApiConstants.CATEGORY_NOT_FOUND_MESSAGE, null, null),
						HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS,
					ApiConstants.UPDATE_ERROR_MESSAGE, Map.of(ApiConstants.EXCEPTION, e.getMessage()), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// --------------------------------------------------------DELETE---------------------------------------------------------------------------
	@Transactional
	public ResponseEntity<AwsshopApiResponse<CategoryDto>> deleteById(Long id) {

		try {
			Optional<Category> categoryOptional = categoryRepository.findById(id);
			if (categoryOptional.isPresent()) {
				Category category = categoryOptional.get();
				categoryRepository.delete(category);

				CategoryDto deletedCategory = utilsCategory.convertToDto(category);

				return new ResponseEntity<>(
						new AwsshopApiResponse<>(ApiConstants.SUCCESS_STATUS, ApiConstants.CATEGORY_DELETE_SUCESS, null, deletedCategory),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS, ApiConstants.CATEGORY_NOT_FOUND_MESSAGE, null, null),
						HttpStatus.OK);
			}

		} catch (RuntimeException e) {
			return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS,
					ApiConstants.CATEGORY_DELETE_ERROR_MESSAGE, Map.of(ApiConstants.EXCEPTION, e.getMessage()), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}