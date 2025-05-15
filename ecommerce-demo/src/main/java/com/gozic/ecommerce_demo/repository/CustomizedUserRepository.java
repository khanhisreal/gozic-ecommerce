package com.gozic.ecommerce_demo.repository;

import com.gozic.ecommerce_demo.entity.User;

public interface CustomizedUserRepository {

    User findByUsername(String username);

    User findByEmail(String email);

}
