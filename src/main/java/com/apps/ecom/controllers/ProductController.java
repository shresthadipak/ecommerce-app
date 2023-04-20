package com.apps.ecom.controllers;

import com.apps.ecom.entities.Product;
import com.apps.ecom.payloads.ApiResponse;
import com.apps.ecom.payloads.ProductDto;
import com.apps.ecom.services.FileService;
import com.apps.ecom.services.ProductService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${project.productImage}")
    private String path;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> addNewProduct(
            @RequestParam("title") String title,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("price") Long price,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        ProductDto productDto = new ProductDto();
        productDto.setTitle(title);
        productDto.setQuantity(quantity);
        productDto.setPrice(price);
        productDto.setDescription(description);
        String fileName = this.fileService.uploadImage(path, image);
        productDto.setProductImage(fileName);
        ProductDto newProduct = this.productService.addNewProduct(productDto);
        return new ResponseEntity<ProductDto>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestParam("title") String title,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("price") Long price,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer productId

    ) throws IOException {
        ProductDto productDto = new ProductDto();
        productDto.setTitle(title);
        productDto.setQuantity(quantity);
        productDto.setPrice(price);
        productDto.setDescription(description);
        String fileName = this.fileService.uploadImage(path, image);
        productDto.setProductImage(fileName);
        ProductDto newProduct = this.productService.updateProduct(productDto, productId);
        return ResponseEntity.ok(newProduct);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        this.productService.deleteProduct(productId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Product deleted successfully !!", true), HttpStatus.OK);
    }

}
