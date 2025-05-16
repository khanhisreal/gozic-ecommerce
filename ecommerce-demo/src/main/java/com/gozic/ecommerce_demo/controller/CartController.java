package com.gozic.ecommerce_demo.controller;

import com.gozic.ecommerce_demo.entity.Cart;
import com.gozic.ecommerce_demo.entity.CartItem;
import com.gozic.ecommerce_demo.entity.Product;
import com.gozic.ecommerce_demo.entity.User;
import com.gozic.ecommerce_demo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private UserRepository userRepository;

    public CartController(ProductRepository productRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") int productId, HttpSession session, Principal principal) {

        System.out.println(productId);

        //Check if user is logged in
        if (principal == null) {
            return "redirect:/signIn";
        }

        //Get logged-in user from the username
        User user = userRepository.findByUsername(principal.getName());
        if (user == null) {
            return "redirect:/signIn";
        }

        //Get product by product id
        Product product = productRepository.findById(productId).get();

        //Get user's cart
        Cart cart = cartRepository.findByUser(user).get();

        //Check if cart already contains this product
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        CartItem existingCartItem = null;

        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == productId) {
                existingCartItem = item;
                break;
            }
        }

        if (existingCartItem != null) {
            // Increase quantity by 1
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            cartItemRepository.save(existingCartItem);
        } else {
            // Create a new cart item with quantity 1
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(1);
            cartItemRepository.save(newItem);
        }

        return "redirect:/home";
    }

}
