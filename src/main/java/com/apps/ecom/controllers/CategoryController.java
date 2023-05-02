package com.apps.ecom.controllers;

import com.apps.ecom.payloads.ApiResponse;
import com.apps.ecom.payloads.CategoryDto;
import com.apps.ecom.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addCategory")
    public ResponseEntity<CategoryDto> addCategory(
            @Valid @RequestBody CategoryDto categoryDto
    ){
        CategoryDto newCategory = this.categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categoryDto = this.categoryService.getAllCategories();
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        return ResponseEntity.ok(updateCategory);
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category deleted Successfully!!", true), HttpStatus.OK);
    }

    @GetMapping("/getSingleCategory/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId){
        CategoryDto categoryDto = this.categoryService.getSingleCategory(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

}
