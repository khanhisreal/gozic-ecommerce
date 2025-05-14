package com.gozic.ecommerce_demo.repository;

import com.gozic.ecommerce_demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
