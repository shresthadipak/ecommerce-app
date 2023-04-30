package com.apps.ecom.controllers;

import com.apps.ecom.entities.Product;
import com.apps.ecom.payloads.ApiResponse;
import com.apps.ecom.payloads.ProductDto;
import com.apps.ecom.services.FileService;
import com.apps.ecom.services.ProductService;
import com.apps.ecom.utils.ValidImage;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import static com.apps.ecom.config.AppConstants.CROSS_ORIGIN_BASE_URL;

@CrossOrigin(CROSS_ORIGIN_BASE_URL)
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

    @PostMapping(value = "/addProduct")
    public ResponseEntity<ProductDto> addNewProduct(
            @RequestParam("title") @NotBlank @Size(min=3, max=50) String title,
            @RequestParam("quantity") @Min(value = 1) Integer quantity,
            @RequestParam("price") @Min(value = 1) Double price,
            @RequestParam("description")  @NotBlank @Size(min=10, max=1000) String description,
            @RequestParam("image") @ValidImage MultipartFile image,
            @RequestParam("categoryId")  Integer categoryId
    ) throws IOException {
        ProductDto productDto = new ProductDto();
        productDto.setTitle(title);
        productDto.setQuantity(quantity);
        productDto.setPrice(price);
        productDto.setDescription(description);
        String fileName = this.fileService.uploadImage(path, image);
        productDto.setProductImage(fileName);
        ProductDto newProduct = this.productService.addNewProduct(productDto, categoryId);
        return new ResponseEntity<ProductDto>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestParam("title") @NotBlank @Size(min=3, max = 50) String title,
            @RequestParam("quantity") @Min(value = 1)  Integer quantity,
            @RequestParam("price") @Min(value = 1) Double price,
            @RequestParam("description") @NotBlank @Size(min=10, max=1000) String description,
            @RequestParam("image") @ValidImage MultipartFile image,
            @RequestParam("categoryId") Integer categoryId,
            @PathVariable Integer productId
    ) throws IOException {
        ProductDto productDto = new ProductDto();
        productDto.setTitle(title);
        productDto.setQuantity(quantity);
        productDto.setPrice(price);
        productDto.setDescription(description);
        String fileName = this.fileService.uploadImage(path, image);
        productDto.setProductImage(fileName);
        ProductDto newProduct = this.productService.updateProduct(productDto, categoryId, productId);
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

    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException{
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }


}
