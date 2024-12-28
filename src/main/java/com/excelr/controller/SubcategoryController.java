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

import com.excelr.model.Product;
import com.excelr.model.Subcategory;
import com.excelr.service.SubcategoryService;

@RestController
public class SubcategoryController {
	@Autowired
	SubcategoryService subCategoryService;
	@GetMapping("/api/subcategory")
	public ResponseEntity<Map<String, Object>> produtsBySubCtegoryId(){
		List<Product> subCategory=subCategoryService.getProductsBySubCategoryId(1L);
		List<Subcategory> subCategories=subCategoryService.getSubCategories();
		Map<String, Object> response=new HashMap<>();
		response.put("subCategory", subCategory);
		response.put("subCategories", subCategories);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/api/subcategory{id}")
	public Subcategory subCategoryById(@PathVariable Long id) {
		Subcategory subCategories= subCategoryService.getSubCategoriesById(id);
		return subCategories;
	}
	@PutMapping("/api/subcategory{id}")
    public ResponseEntity<Subcategory> updateSubcategory(@PathVariable Long id, @RequestBody Subcategory subcategory) {
        return ResponseEntity.ok(subCategoryService.updateSubcategory(id, subcategory));
    }
    @DeleteMapping("/api/subcategory{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Long id) {
    	subCategoryService.deleteSubcategory(id);
        return ResponseEntity.noContent().build();
    }
}
