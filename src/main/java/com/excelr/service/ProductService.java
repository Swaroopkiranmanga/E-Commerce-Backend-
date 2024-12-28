package com.excelr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.excelr.model.Product;
import com.excelr.model.Productdto;
import com.excelr.model.Subcategory;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.excelr.repository.ProductRepository;
import com.excelr.repository.SubcategoryRepository;
import com.excelr.util.S3Util;

@Service
public class ProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;
    
    @Autowired
    private S3Util s3Util;
    
    public ProductService(ProductRepository productRepository, SubcategoryRepository subcategoryRepository) {
        this.productRepository = productRepository;
		this.subcategoryRepository = subcategoryRepository;
    }

    public Page<Productdto> getProductsBySubcategory(Long subcategoryId, Pageable pageable) {
        return productRepository.findBySubcategoryId(subcategoryId, pageable)
                .map(this::mapProductToProductDTO);
    }

    public Productdto getProductById(long id) {
    	Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapProductToProductDTO(product);
    }

    public Productdto createProduct(Productdto productDto, MultipartFile image) {
        Subcategory subcategory = subcategoryRepository.findById(productDto.getSubcategoryId())
            .orElseThrow(() -> new RuntimeException(
                "Subcategory not found with id: " + productDto.getSubcategoryId()
            ));
            
        String imageUrl = s3Util.uploadImage(image);

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setBrand(productDto.getBrand());
        product.setImage(imageUrl); 
        product.setRating(productDto.getRating());
        product.setQuantity(productDto.getQuantity());
        product.setSubcategory(subcategory);

        Product savedProduct = productRepository.save(product);
        return mapProductToProductDTO(savedProduct);
    }

    public Productdto updateProduct(long id, Productdto productDto, MultipartFile image) {
    	Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    "Product not found with id: " + id
                ));
            Subcategory subcategory = null;
            if (productDto.getSubcategoryId() != null) {
                subcategory = subcategoryRepository.findById(productDto.getSubcategoryId())
                    .orElseThrow(() -> new RuntimeException(
                        "Subcategory not found with id: " + productDto.getSubcategoryId()
                    ));
            }
            String imageUrl = existingProduct.getImage();
            if (image != null && !image.isEmpty()) {
                try {
                    imageUrl = s3Util.updateImage(existingProduct.getImage(), image);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to update product image: " + e.getMessage());
                }
            }
            existingProduct.setName(productDto.getName() != null ? productDto.getName() : existingProduct.getName());
            existingProduct.setPrice(productDto.getPrice() != 0 ? productDto.getPrice() : existingProduct.getPrice());
            existingProduct.setDescription(productDto.getDescription() != null ? productDto.getDescription() : existingProduct.getDescription());
            existingProduct.setBrand(productDto.getBrand() != null ? productDto.getBrand() : existingProduct.getBrand());
            existingProduct.setImage(imageUrl);
            existingProduct.setRating(productDto.getRating() != null ? productDto.getRating() : existingProduct.getRating());
            existingProduct.setQuantity(productDto.getQuantity() != null ? productDto.getQuantity() : existingProduct.getQuantity());
            
            if (subcategory != null) {
                existingProduct.setSubcategory(subcategory);
            }
            Product updatedProduct = productRepository.save(existingProduct);
            logger.info("Successfully updated product with id: {}", id);
            return mapProductToProductDTO(updatedProduct);
    }



    public void deleteProduct(long id) {
    	 Product product = productRepository.findById(id)
    	            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    	        try {
    	            if (product.getImage() != null) {
    	                s3Util.deleteImage(product.getImage());
    	            }
    	        } catch (Exception e) {
    	        	 logger.error("Failed to delete image from S3 for product id: {}. Error: {}", id, e.getMessage());
    	        }
    	        productRepository.delete(product);
    	        logger.info("Successfully deleted product with id: {}", id);
    	    }

	public List<Productdto> searchProducts(String keyword) {
		List<Product> products=productRepository.searchProducts(keyword);
		return products.stream()
				.map(this::mapProductToProductDTO) 
				.collect(Collectors.toList());
	}
    
	private Productdto mapProductToProductDTO(Product product) {
	    return new Productdto(
	            product.getId(),
	            product.getName(),
	            product.getPrice(),
	            product.getDescription(),
	            product.getSubcategory() != null ? product.getSubcategory().getId() : null, 
	            product.getSubcategory() != null ? product.getSubcategory().getName() : null, 
	            product.getBrand(),
	            product.getImage(),
	            product.getRating(),
	            product.getQuantity()
	    );
	}

}