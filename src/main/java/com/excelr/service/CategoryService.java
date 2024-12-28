package com.excelr.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelr.model.Category;
import com.excelr.model.Subcategory;
import com.excelr.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
    private CategoryRepository categoriesRepo;

    public List<Subcategory> getSubCategoriesByCategoryId(Long categoryId) {
        Optional<Category> categoryopt = categoriesRepo.findById(categoryId);
        if(categoryopt.isPresent()) {
        	Category category= categoryopt.get();
        	return category.getSubCategory();
        }else {
        	return Collections.emptyList();
        }
    }
    public List<Category> getCategories() {
    	return  categoriesRepo.findAll();
    }
    public Category getCategoryById(Long id) {
        Optional<Category> category= categoriesRepo.findById(id);
        if(category.isPresent()) {
        	return category.get();
        }else {
        	return (Category) Collections.emptyList();
        }
    }
    public Category createCategory(Category category) {
        return categoriesRepo.save(category);
    }
    public Category updateCategory(Long id, Category updatedCategory) {
    	Category category = getCategoryById(id);
        category.setName(updatedCategory.getName());
        return categoriesRepo.save(category);
    }
    public void deleteCategory(Long id) {
    	categoriesRepo.deleteById(id);
    } 
}
