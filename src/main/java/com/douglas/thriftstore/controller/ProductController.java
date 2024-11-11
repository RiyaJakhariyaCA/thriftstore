package com.douglas.thriftstore.controller;

import com.douglas.thriftstore.model.Product;
import com.douglas.thriftstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:8080/")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create a new product
    @PostMapping
    public ResponseEntity<?> uploadProduct(
    		@RequestParam("name") String name,@RequestParam("description") String description,
    		@RequestParam("sellerId") String sellerId,@RequestParam("price") double price,
    		@RequestParam("condition") int condition,@RequestParam("discount") int discount,
    		@RequestParam("available") boolean available,
    		
            @RequestParam(value="files",required = false) MultipartFile[] files) {   
    	try {
    		Product product = new Product(name,price,description,condition,discount,sellerId,available);
            Product createdProduct = productService.createProduct(product,files);
            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            		.body("Error in creating orders: " + e.getMessage());
        }
    }

    // Get all products
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving orders: " + e.getMessage()); 
        }
    }

    // Get product by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            return product.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving the order with ID: " + id + ", " + e.getMessage()); 
        }
    }

    // Update an existing product
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,@RequestParam("description") String description,
    		@RequestParam("sellerId") String sellerId,@RequestParam("price") double price,
    		@RequestParam("condition") int condition,@RequestParam("discount") int discount,
    		@RequestParam("available") boolean available,
    		
            @RequestParam(value="files",required = false) MultipartFile[] files) {
        try {
        	Product product = new Product(name,price,description,condition,discount,sellerId,available);
            Product updatedProduct = productService.updateProduct(id, product, files);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error in updating product: " + e.getMessage());
        }
    }

    // Delete a product by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        try {
            boolean isDeleted = productService.deleteProduct(id);
            return isDeleted ? ResponseEntity.ok("Deleted Successfully") 
                    :  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found with ID: " + id); 
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the order with ID: " + id + ", " + e.getMessage());

        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam("keyword") String keyword) {
        try {
            List<Product> products = productService.searchProductsByKeyword(keyword);
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build(); // No content found for keyword
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting the order with ID: " +e.getMessage());

        }
    }

}
