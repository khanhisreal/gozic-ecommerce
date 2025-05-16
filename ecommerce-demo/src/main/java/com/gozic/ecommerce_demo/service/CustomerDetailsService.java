package com.gozic.ecommerce_demo.service;

import com.gozic.ecommerce_demo.entity.User;
import com.gozic.ecommerce_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomerDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userDb = userRepository.findByUsername(username);

        if(userDb == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(userDb.getUsername())
                .password(userDb.getPassword())
                .roles("USER")
                .build();
    }
}
