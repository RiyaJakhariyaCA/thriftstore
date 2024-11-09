package com.douglas.thriftstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.douglas.thriftstore.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	 List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String nameKeyword, String descriptionKeyword);
}
