package com.douglas.thriftstore.service;

import com.douglas.thriftstore.model.Product;
import com.douglas.thriftstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    private String uploadDirectory = "E:/Images/";

   
    // Create a new product
    public Product createProduct(Product product,MultipartFile file) throws Exception {
    	 File directory = new File(uploadDirectory);
         if (!directory.exists()) {
             directory.mkdirs();  // Create directory if it doesn't exist
         }

         String originalFileName = file.getOriginalFilename();
         long randomFileName = ThreadLocalRandom.current().nextLong(100000, 999999);
         String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
         Path filePath = Paths.get(uploadDirectory + randomFileName+"."+fileExtension);
         
         // Save the file to disk
         file.transferTo(filePath.toFile());

         // Set the file path in the product object
         product.setFilePath(filePath.toString());
         return productRepository.save(product);
        
    }

    
    public Optional<Product> getProductById(long id) {
       
            return productRepository.findById(id);
       
    }

    // Get all products
    public List<Product> getAllProducts() {
       
            return productRepository.findAll();
       
    }

    // Update an existing product
    public Product updateProduct(long id, Product updatedProduct) {
       
            if (productRepository.existsById(id)) {
                updatedProduct.setId(id); // Ensure we update the correct product by ID
                return productRepository.save(updatedProduct);
            }
            return null; // Product not found
       
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
