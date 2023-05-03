package com.apps.ecom.services.impl;

import com.apps.ecom.entities.Category;
import com.apps.ecom.exceptions.ResourceNotFoundException;
import com.apps.ecom.payloads.CategoryDto;
import com.apps.ecom.repositories.CategoryRepo;
import com.apps.ecom.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category newCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(newCategory, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> allCategories = this.categoryRepo.findAll(Sort.by(Sort.Direction.DESC, "categoryId"));
        List<CategoryDto> categoryDtos = allCategories.stream().map((category)-> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updateCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(updateCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getSingleCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
        return this.modelMapper.map(category, CategoryDto.class);
    }
}
