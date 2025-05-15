package com.gozic.ecommerce_demo.repository;

import com.gozic.ecommerce_demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    //inner join product with productCategories
    @Query("SELECT p FROM Product p JOIN p.productCategories pc WHERE pc.category.categoryId = :categoryId")
    List<Product> findProductsByCategoryId(@Param("categoryId") int categoryId);

}
