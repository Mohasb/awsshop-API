package com.muhammadhh.awsshop.services.product;

import java.util.HashMap;
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
import com.muhammadhh.awsshop.models.Media;
import com.muhammadhh.awsshop.models.Product;
import com.muhammadhh.awsshop.models.dto.CategoryDto;
import com.muhammadhh.awsshop.models.dto.ProductDto;
import com.muhammadhh.awsshop.respositories.CategoryRepository;
import com.muhammadhh.awsshop.respositories.ProductRepository;
import com.muhammadhh.awsshop.utils.ApiConstants;
import com.muhammadhh.awsshop.utils.responses.AwsshopApiResponse;

import jakarta.validation.Valid;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;

	// --------------------------------------------------------GETALL---------------------------------------------------------------------------
	public ResponseEntity<AwsshopApiResponse<List<ProductDto>>> getAllProducts() {

		try {
			List<Product> productsList = productRepository.findAll();
			List<ProductDto> productsDtoList = productsList.stream()
					.map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());

			if (!productsList.isEmpty()) {
				return new ResponseEntity<>(
						new AwsshopApiResponse<>("SUCCESS", "List of all products", null, productsDtoList),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new AwsshopApiResponse<>("SUCCESS", "There are no products", null, productsDtoList),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			Map<String, Object> errorDetails = new HashMap<>();
			errorDetails.put("exception", e.getMessage());

			return new ResponseEntity<>(
					new AwsshopApiResponse<>("ERROR", "An error occurred while fetching products", errorDetails, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// --------------------------------------------------------GET(ID)---------------------------------------------------------------------------
	public ResponseEntity<AwsshopApiResponse<ProductDto>> getProductById(Long id) {

		try {
			Optional<Product> productOptional = productRepository.findById(id);

			if (productOptional.isPresent()) {
				ProductDto productDto = modelMapper.map(productOptional, ProductDto.class);
				return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.SUCCESS_STATUS,
						ApiConstants.PRODUCT_FOUND_MESSAGE, null, productDto), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS,
						ApiConstants.PRODUCT_NOT_FOUND_MESSAGE, null, null), HttpStatus.NOT_FOUND);
			}

		} catch (RuntimeException e) {
			return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS, ApiConstants.SERVER_ERROR,
					Map.of(ApiConstants.EXCEPTION, e.getMessage()), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// --------------------------------------------------------POST---------------------------------------------------------------------------
	public ResponseEntity<AwsshopApiResponse<ProductDto>> saveProduct(ProductDto productDto) {

		try {
			Product product = modelMapper.map(productDto, Product.class);

			Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());

			category.ifPresent(product::setCategory);

			// Mapeo de MediaDto a Media y configuración en el producto
			List<Media> mediaList = productDto.getMedia().stream()
					.map(mediaDto -> modelMapper.map(mediaDto, Media.class)).collect(Collectors.toList());
			product.setMedia(mediaList);

			Product savedProduct = productRepository.save(product);

			ProductDto productDtoSaved = modelMapper.map(savedProduct, ProductDto.class);

			return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.SUCCESS_STATUS,
					ApiConstants.PRODUCT_CREATE_SUCCESS, null, productDtoSaved), HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS, ApiConstants.SERVER_ERROR,
					Map.of(ApiConstants.EXCEPTION, e.getMessage()), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// --------------------------------------------------------PUT---------------------------------------------------------------------------
	// TODO put
	public ResponseEntity<?> putProduct(Long id, ProductDto productDto) {
		try {
			Optional<Product> existingProductOpt = productRepository.findById(id);

			if (existingProductOpt.isPresent()) {
				Product existingProduct = existingProductOpt.get();

				// Map the fields from productDto to existingProduct
				modelMapper.map(productDto, existingProduct);

				// Handle the category separately if categoryId is present in the productDto
				if (productDto.getCategoryId() != null) {
					Category category = categoryRepository.findById(productDto.getCategoryId())
							.orElseThrow(() -> new RuntimeException("Category not found"));
					existingProduct.setCategory(category);
				}

				// Actualizar la lista de media
				List<Media> mediaList = productDto.getMedia().stream()
						.map(mediaDto -> modelMapper.map(mediaDto, Media.class)).collect(Collectors.toList());
				existingProduct.setMedia(mediaList);

				// Save the updated product
				Product updatedProduct = productRepository.save(existingProduct);

				// Convert updatedProduct to ProductDto
				ProductDto updatedProductDto = modelMapper.map(updatedProduct, ProductDto.class);

				return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.SUCCESS_STATUS,
						ApiConstants.UPDATE_SUCCESS_MESSAGE, null, updatedProductDto), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS,
						"Product with id=" + id + " not found", null, null), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new AwsshopApiResponse<>(ApiConstants.ERROR_STATUS,
					"An error occurred while updating the product", Map.of("exception", e.getMessage()), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// --------------------------------------------------------DELETE---------------------------------------------------------------------------
	public ResponseEntity<?> deleteProduct(Long id) {
		try {
			Optional<Product> productOptional = productRepository.findById(id);
			if (productOptional.isPresent()) {
				productRepository.deleteById(id);
				return new ResponseEntity<>(new AwsshopApiResponse<>("SUCCESS", "Product deleted successfully", null,
						productOptional.get()), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new AwsshopApiResponse<>("ERROR", "Product with id=" + id + " not found", null, null),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			Map<String, Object> errorDetails = new HashMap<>();
			errorDetails.put("exception", e.getMessage());

			return new ResponseEntity<>(new AwsshopApiResponse<>("ERROR",
					"An error occurred while deleting the product", errorDetails, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
