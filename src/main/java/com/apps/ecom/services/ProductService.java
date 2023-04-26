package com.apps.ecom.services;

import com.apps.ecom.payloads.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductDto addNewProduct(ProductDto productDto, Integer categoryId);

    ProductDto updateProduct(ProductDto productDto, Integer categoryId, Integer productId);

    void deleteProduct(Integer productId);

    List<ProductDto> getAllProducts();

    ProductDto getSingleProduct(Integer productId);

}
