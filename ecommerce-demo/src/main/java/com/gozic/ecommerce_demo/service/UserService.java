package com.gozic.ecommerce_demo.service;

import com.gozic.ecommerce_demo.entity.Cart;
import com.gozic.ecommerce_demo.entity.User;
import com.gozic.ecommerce_demo.repository.CartRepository;
import com.gozic.ecommerce_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private CartRepository cartRepository;
    private UserRepository userRepository;

    @Autowired
    public UserService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    //save User and its Cart
    public User saveUsersWithCart(User user) {

        //just in case there's an id in User object ... set id to 0
        user.setUserId(0);

        //Create a new Cart for user
        Cart cart = new Cart(user);

        //set the cart in the User entity (bi-directional relationship)
        user.setCart(cart);

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserById(int theId) {
        User user = new User();

        Optional<User> value = userRepository.findById(theId);

        if (value.isPresent()) {
            user = value.get();
        } else {
            user = null;
        }
        return user;
    }

    // id != 0 -> update
    public User updateUser(User user) {
        if (userRepository.existsById(user.getUserId())) {
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found for id: " + user.getUserId());
        }
    }

    public void deleteUserByUserEntity(User user) {
        userRepository.delete(user);
    }

}
