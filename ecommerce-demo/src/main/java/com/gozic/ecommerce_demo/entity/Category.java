package com.gozic.ecommerce_demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Setter
@Getter
@AllArgsConstructor
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ProductCategory> productCategories;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    //convenient method for bi-directional relationship
    public void addProduct(Product product) {

        if (productCategories == null) {
            productCategories = new ArrayList<>();
        }

        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategory(this);
        productCategory.setProduct(product);
        this.productCategories.add(productCategory);
    }

}

