package com.excelr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excelr.model.Category;
import com.excelr.model.Subcategory;
import com.excelr.service.CategoryService;
@RestController
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@GetMapping("/api/get/categories")
	public ResponseEntity<Map<String, Object>> categoriesAndSubCategories(){
		List<Category> categories =categoryService.getCategories();
		List<Subcategory> subcategory= categoryService.getSubCategoriesByCategoryId(1L);
		Map <String,Object> response= new HashMap<>();
		response.put("categories", categories);
		response.put("subcategory", subcategory);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/api/get/categories/id")
	public ResponseEntity<Map<String, Object>> categoriesAndSubCategory(Long categoryId){
		Category categories =categoryService.getCategoryById(categoryId);
		List<Subcategory> subcategory= categoryService.getSubCategoriesByCategoryId(1L);
		Map <String,Object> response= new HashMap<>();
		response.put("categories", categories);
		response.put("subcategory", subcategory);
		return ResponseEntity.ok(response);
	}
	@GetMapping("api/category{id}")
	public Category singleCategory(@PathVariable Long id) {
		Category category=categoryService.getCategoryById(id);
		return category;
	}
	@PutMapping("/api/category{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }
    @DeleteMapping("/api/category{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
