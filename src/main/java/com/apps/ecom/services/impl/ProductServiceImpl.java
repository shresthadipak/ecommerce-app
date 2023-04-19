package com.apps.ecom.services.impl;

import com.apps.ecom.entities.Product;
import com.apps.ecom.payloads.ProductDto;
import com.apps.ecom.repositories.ProductRepo;
import com.apps.ecom.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto addNewProduct(ProductDto productDto) {
        Product product = this.modelMapper.map(productDto, Product.class);
        Product newProduct = this.productRepo.save(product);
        return this.modelMapper.map(newProduct, ProductDto.class);
    }
}
