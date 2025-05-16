package com.gozic.ecommerce_demo.service;

import com.gozic.ecommerce_demo.entity.Cart;
import com.gozic.ecommerce_demo.entity.CartItem;
import com.gozic.ecommerce_demo.entity.Product;
import com.gozic.ecommerce_demo.entity.User;
import com.gozic.ecommerce_demo.repository.CartItemRepository;
import com.gozic.ecommerce_demo.repository.CartRepository;
import com.gozic.ecommerce_demo.repository.ProductRepository;
import com.gozic.ecommerce_demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Cart getCartByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Username not found - " + username);
        }

        Cart cart = cartRepository.findByUser(user).orElse(null);

        if (cart == null) {
            cart = new Cart(user);
            user.setCart(cart);
            cartRepository.save(cart);
            userRepository.save(user);
        }

        return cart;
    }

    //transactions ensure that a series of operations either all succeed or all fail
    @Transactional
    public void addProductToCart(User user, int productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quality must be greater than 0");
        }

        //fetch user's cart
        Cart cart = user.getCart();

        //fetch the product from the db
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        //check if product is already in cart
        CartItem existingItem = null;
        if (cart.getCartItems() != null) {
            existingItem = cart.getCartItems()
                    .stream()
                    .filter(item -> item.getProduct().getProductId() == productId)
                    .findFirst()
                    .orElse(null);
        }

        if (existingItem != null) {
            //if there already exists that product in the cart
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            //create new cart item and add to cart
            CartItem newItem = new CartItem(cart, product, quantity);
            cart.add(newItem);
        }

        //save the updated cart
        cartRepository.save(cart);
    }

}
