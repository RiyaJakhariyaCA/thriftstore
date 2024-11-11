package com.douglas.thriftstore.service;

import com.douglas.thriftstore.model.Product;
import com.douglas.thriftstore.repository.ProductRepository;
import com.douglas.thriftstore.utils.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
   

   
    // Create a new product
    public Product createProduct(Product product,MultipartFile[] file) throws Exception {
    		uploadFile(file,product);
         return productRepository.save(product);
        
    }
    
    public void uploadFile(MultipartFile[] files,Product product) throws Exception {
      
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file != null && !file.isEmpty()) {
            String filePath = FileUtils.saveFile(file);

            // Assign file path to the corresponding field in the Product entity
            switch (i) {
                case 0: product.setFilePath1(filePath.toString()); break;
                case 1: product.setFilePath2(filePath.toString()); break;
                case 2: product.setFilePath3(filePath.toString()); break;
            }
            }
        }
    }

    
    public Optional<Product> getProductById(long id) {
       
            return productRepository.findById(id);
       
    }

    // Get all products
    public List<Product> getAllProducts() {
       
            return productRepository.findAll();
       
    }

    // Update an existing product
    public Product updateProduct(Long id, Product newProductData, MultipartFile[] files) throws Exception {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                if (file != null && !file.isEmpty()) {
                    String filePath = FileUtils.saveFile(file);
                    switch (i) {
                        case 0:
                            existingProduct.setFilePath1(filePath);
                            break;
                        case 1:
                            existingProduct.setFilePath2(filePath);
                            break;
                        case 2:
                            existingProduct.setFilePath3(filePath);
                            break;
                    }
                }
            }
        }
        
        existingProduct.setName(newProductData.getName());
        existingProduct.setAvailable(newProductData.isAvailable());
        existingProduct.setCondition(newProductData.getCondition());
        existingProduct.setPrice(newProductData.getPrice());
        existingProduct.setDiscount(newProductData.getDiscount());
        existingProduct.setSellerId(newProductData.getSellerId());
        existingProduct.setDescription(newProductData.getDescription());
        
        return productRepository.save(existingProduct);
    }


    // Delete a product by id
    public boolean deleteProduct(long id) {
       
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                return true;
            }
            return false; // Product not found
        
    }
    
    public List<Product> searchProductsByKeyword(String keyword) {
        try {
            return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("Error searching products", e);
        }
    }
}
