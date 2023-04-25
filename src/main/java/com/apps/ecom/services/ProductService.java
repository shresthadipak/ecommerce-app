package com.apps.ecom.services;

import com.apps.ecom.payloads.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductDto addNewProduct(String productDto, String image, Integer categoryId) throws JsonProcessingException;

    ProductDto updateProduct(ProductDto productDto, Integer productId);

    void deleteProduct(Integer productId);

    List<ProductDto> getAllProducts();

    ProductDto getSingleProduct(Integer productId);

}
