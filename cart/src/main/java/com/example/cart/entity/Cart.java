package com.example.cart.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

import java.util.List;

@Document(collection = "cart")
public class Cart {
    @Id
    private String userName;
    private float totalCost;
    private List<CartItem> cartItemList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

}
