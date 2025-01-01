package com.excelr.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excelr.model.Category;
import com.excelr.service.CategoryService;
@RestController
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@GetMapping("/api/get/categories")
	public ResponseEntity<?> categoriesAndSubCategories(){
		return categoryService.getCategories();
	}
	@GetMapping("api/category{id}")
	public ResponseEntity<?> singleCategory(@PathVariable Long id) {
		return categoryService.getCategoryById(id);
	}
	@PutMapping("/api/category{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }
    @DeleteMapping("/api/category{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
    @PostMapping("/api/category")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }
}
