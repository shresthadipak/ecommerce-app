package com.apps.ecom.services.impl;

import com.apps.ecom.entities.Product;
import com.apps.ecom.exceptions.ResourceNotFoundException;
import com.apps.ecom.payloads.ProductDto;
import com.apps.ecom.repositories.ProductRepo;
import com.apps.ecom.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        product.setProductImage(productDto.getProductImage());
        Product updateProduct = this.productRepo.save(product);
        return this.modelMapper.map(updateProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "productId", productId));
        this.productRepo.delete(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = this.productRepo.findAll();
        List<ProductDto> productDtos = allProducts.stream().map((product) -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        return productDtos;
    }

    @Override
    public ProductDto getSingleProduct(Integer productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
        return this.modelMapper.map(product, ProductDto.class);
    }
}
