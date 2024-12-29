package com.excelr.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.excelr.model.Productdto;
import com.excelr.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Productdto>> getProductsBySubcategory(
            @RequestParam(required = false) Long subcategoryId,
            @PageableDefault(size = 20) Pageable pageable) {
    		Page<Productdto> products;
    	if(subcategoryId!=null) {
    		products = productService.getProductsBySubcategory(subcategoryId, pageable);}
    	else {
    		products=productService.getProducts(pageable);
    	}
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Productdto> getProductById(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Productdto> createProduct(
            @RequestPart("product") Productdto productDto,
            @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(productService.createProduct(productDto, image));
    }
 
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Productdto> updateProduct(
            @PathVariable Long id,
            @RequestPart(value = "product") Productdto productDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(productService.updateProduct(id, productDto, image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Productdto>> searchProducts(@RequestParam String keyword){
    	List<Productdto> products = productService.searchProducts(keyword);
		return new ResponseEntity<>(products, HttpStatus.OK);	
    }
}