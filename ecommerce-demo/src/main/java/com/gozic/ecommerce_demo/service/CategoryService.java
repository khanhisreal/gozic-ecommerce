package com.gozic.ecommerce_demo.service;

import com.gozic.ecommerce_demo.entity.Category;
import com.gozic.ecommerce_demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findById(int theId) {

        Category category = new Category();

        Optional<Category> value = categoryRepository.findById(theId);

        if (value.isPresent()) {
            category = value.get();
        } else {
            category = null;
        }
        return category;

    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // id = 0 -> add
    public Category addCategory(Category category) {

        //just in case there's an id in Category object ... set id to 0
        category.setCategoryId(0);

        return categoryRepository.save(category);
    }

    // id != 0 -> update
    public Category updateCategory(Category category) {
        if (categoryRepository.existsById(category.getCategoryId())) {
            return categoryRepository.save(category);
        } else {
            throw new RuntimeException("Category not found for id: " + category.getCategoryId());
        }
    }

    public void deleteCategory(int theId) {
        categoryRepository.deleteById(theId);
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }

}
