package com.gozic.ecommerce_demo.repository;

import com.gozic.ecommerce_demo.entity.Cart;
import com.gozic.ecommerce_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUser(User user);

}
