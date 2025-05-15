package com.gozic.ecommerce_demo.repository;

import com.gozic.ecommerce_demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {

    EntityManager entityManager;

    @Autowired
    public CustomizedUserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUsername(String username) {

        User userDb = new User();

        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username =:theData", User.class);
            query.setParameter("theData", username);

            userDb = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return userDb;
    }

    @Override
    public User findByEmail(String email) {

        User userDb = new User();

        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email =:theData", User.class);
            query.setParameter("theData", email);

            userDb = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return userDb;
    }

}
