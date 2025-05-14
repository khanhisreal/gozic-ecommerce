package com.gozic.ecommerce_demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false)
    private int cartId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //bi-directional one to many
    @OneToMany(mappedBy = "cart", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<CartItem> cartItems;

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    //convenience methods for bi-directional relationship
    public void add(CartItem item) {

        if(cartItems == null) {
            cartItems = new ArrayList<>();
        }

        cartItems.add(item);

        //bi-directional relationship -> set a Cart that a cart item belongs to
        item.setCart(this);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", user=" + user +
                '}';
    }
}
