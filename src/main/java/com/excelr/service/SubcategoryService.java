package com.excelr.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelr.model.Product;
import com.excelr.model.Subcategory;
import com.excelr.repository.SubcategoryRepository;

@Service
public class SubcategoryService {
	@Autowired
	SubcategoryRepository subCategoriesRepo;
	public List<Product> getProductsBySubCategoryId(Long subcategoryId){
		Subcategory subCategories=subCategoriesRepo.findById(subcategoryId).orElseThrow(() -> new RuntimeException("Category not found"));
		return subCategories.getProduct();
	}
	public List<Subcategory> getSubCategories(){
		return subCategoriesRepo.findAll();
	}
	public Subcategory getSubCategoriesById(Long id) {
        Optional<Subcategory> subcat= subCategoriesRepo.findById(id);
        if(subcat.isPresent()) {
        	return subcat.get();
        }else {
        	return (Subcategory) Collections.emptyList();
        }
    }
	public Subcategory createSubcategory(Subcategory subcategory) {
        return subCategoriesRepo.save(subcategory);
    }
    public Subcategory updateSubcategory(Long id, Subcategory updatedSubcategory) {
    	Subcategory subcategory = getSubCategoriesById(id);
        subcategory.setName(updatedSubcategory.getName());
        subcategory.setCategory(updatedSubcategory.getCategory());
        return subCategoriesRepo.save(subcategory);
    }
    public void deleteSubcategory(Long id) {
    	subCategoriesRepo.deleteById(id);
    }
}
