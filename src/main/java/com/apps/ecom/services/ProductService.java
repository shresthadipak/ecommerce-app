package com.apps.ecom.services;

import com.apps.ecom.payloads.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto addNewProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto, Integer productId);

    void deleteProduct(Integer productId);

    List<ProductDto> getAllProducts();

    ProductDto getSingleProduct(Integer productId);

}
