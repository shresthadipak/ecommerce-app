package com.apps.ecom.controllers;

import com.apps.ecom.entities.Product;
import com.apps.ecom.payloads.ApiResponse;
import com.apps.ecom.payloads.ProductDto;
import com.apps.ecom.services.FileService;
import com.apps.ecom.services.ProductService;
import com.apps.ecom.utils.ValidImage;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${project.productImage}")
    private String path;

    @PostMapping(value = "/category/{categoryId}/addProduct", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDto> addNewProduct(
//            @RequestParam("title") @NotBlank @Size(min=3, max=50) String title,
//            @RequestParam("quantity") @Min(value = 1) Integer quantity,
//            @RequestParam("price") @Min(value = 1) Long price,
//            @RequestParam("description")  @NotBlank @Size(min=10, max=1000) String description,
            @RequestPart("productDetails") @Valid String productDetails,
            @RequestPart("image") @ValidImage MultipartFile image,
            @PathVariable Integer categoryId
    ) throws IOException {

//        ProductDto productDto = new ProductDto();
//        productDto.setTitle(title);
//        productDto.setQuantity(quantity);
//        productDto.setPrice(price);
//        productDto.setDescription(description);
        String fileName = this.fileService.uploadImage(path, image);
//        productDto.setProductImage(fileName);
        ProductDto newProduct = this.productService.addNewProduct(productDetails, fileName, categoryId);
        return new ResponseEntity<ProductDto>(newProduct, HttpStatus.CREATED);

    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestParam("title") @NotBlank @Size(min=3, max = 50) String title,
            @RequestParam("quantity") @Min(value = 1)  Integer quantity,
            @RequestParam("price") @Min(value = 1) Long price,
            @RequestParam("description") @NotBlank @Size(min=10, max=1000) String description,
            @ValidImage @RequestParam("image") MultipartFile image,
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

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDto = this.productService.getAllProducts();
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable Integer productId){
        ProductDto productDto = this.productService.getSingleProduct(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

}
