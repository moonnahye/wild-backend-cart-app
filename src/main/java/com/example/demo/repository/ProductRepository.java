package com.example.demo.repository;

import com.example.demo.model.Product;
import com.example.demo.model.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId> {
}
