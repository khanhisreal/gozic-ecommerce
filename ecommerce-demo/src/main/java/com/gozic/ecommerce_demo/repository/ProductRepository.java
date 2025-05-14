package com.gozic.ecommerce_demo.repository;

import com.gozic.ecommerce_demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
