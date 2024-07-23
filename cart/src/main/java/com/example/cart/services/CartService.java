package com.example.cart.services;

import com.example.cart.entity.Cart;
import com.example.cart.entity.CartItem;

import java.util.List;

public interface CartService {
    List<CartItem> findByUserName(String userName);
    Cart createCart(String userName);
    Cart addItemToCart(String userName,CartItem cartItem);
    CartItem getItemFromDB(String pid,String did,String type);
    Cart deleteItemFromCart(String userName,CartItem cartItem);
    void mergeCart(String userName,String guest);
}
