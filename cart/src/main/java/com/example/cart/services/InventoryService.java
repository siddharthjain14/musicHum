package com.example.cart.services;

import com.example.cart.entity.CartItem;
import com.example.cart.entity.InventoryItem;

import java.util.List;

public interface InventoryService {
    List<Float> getProductCountAndCost(String pid, String did);
    void setProductCountAndCost(String pid, String did, float count,float cost);
    void clearCart(String userName);
    boolean checkItems(List<CartItem> cartItemList);
    boolean checkItem(CartItem cartItem);
    void checkOut(String userName);
    List<InventoryItem> getInventoryItem(String pid,String type);
    void addItemToInventory(String pid,String did,float cost,float count);
}
