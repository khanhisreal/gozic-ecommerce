package com.gozic.ecommerce_demo.repository;

import com.gozic.ecommerce_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>, CustomizedUserRepository {
}
