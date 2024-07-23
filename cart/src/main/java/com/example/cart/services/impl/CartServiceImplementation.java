package com.example.cart.services.impl;

import com.example.cart.entity.*;
import com.example.cart.repository.CartRepository;
import com.example.cart.repository.InventoryRepository;
import com.example.cart.services.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImplementation implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    InventoryRepository inventoryRepository;

    public List<CartItem> findByUserName(String userName) {
        Cart cart=cartRepository.findByUserName(userName);
        List<CartItem> cartItemList=cart.getCartItemList();
        cartItemList.forEach(cartItem -> {
            if(!inventoryRepository.findByPid(cartItem.getId()).getListOfDid().containsKey(cartItem.getDid())){
                cartItem.setCost(-1);
            }
        });
        cart.setCartItemList(cartItemList);
        cartRepository.save(cart);
        return cartItemList;
    }

    public Cart createCart(String userName) {
        Cart cart = new Cart();
        cart.setUserName(userName);
        cart.setCartItemList(new ArrayList<>());
        return cartRepository.save(cart);
    }

    public Cart addItemToCart(String userName, CartItem cartItem) {
        Cart cart;
        if(cartRepository.findByUserName(userName)==null){
            createCart(userName);
        }
        cart=cartRepository.findByUserName(userName);
        List<CartItem> list = cart.getCartItemList();
        list.add(cartItem);
        cart.setCartItemList(list);
        cartRepository.save(cart);
        return cart;
    }

    @Transactional
    public Cart deleteItemFromCart(String userName,CartItem cartItem){
        Cart cart=cartRepository.findByUserName(userName);
        List<CartItem> list = cart.getCartItemList();
        list.remove(cartItem);
        cart.setCartItemList(list);
        return cart;
    }
    public CartItem getItemFromDB(String pid, String did,String type) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        String result;
        if(type.equals("Song")){
            result=restTemplate.getForObject("http://10.177.1.118:8000/product/{pid}",String.class,pid);
            Product[] product = new Product[1];
            try {
                product = mapper.readValue(result, Product[].class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            CartItem cartItem = new CartItem();
            cartItem.setId(product[0].getPid());
            cartItem.setDid(did);
            cartItem.setAlbum(product[0].getAlbum());
            cartItem.setArtist(product[0].getArtist());
            cartItem.setCoverUrl(product[0].getCoverUrl());
            cartItem.setCost(inventoryRepository.findByPid(pid).getListOfDid().get(did).get(1));
            cartItem.setName(product[0].getTitle());
            cartItem.setType(type);
            return cartItem;
        }
        else{
            result = restTemplate.getForObject("http://10.177.1.118:8000/album/{pid}", String.class, pid);
            Album[] album=new Album[1];
            try {
                album = mapper.readValue(result, Album[].class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            CartItem cartItem = new CartItem();
            cartItem.setId(album[0].getAid());
            cartItem.setDid(did);
            cartItem.setAlbum(album[0].getTitle());
            cartItem.setArtist(album[0].getArtist());
            cartItem.setCoverUrl(album[0].getCoverUrl());
            cartItem.setCost(inventoryRepository.findByPid(pid).getListOfDid().get(did).get(1));
            cartItem.setName(album[0].getTitle());
            cartItem.setType(type);
            return cartItem;
        }

    }
    @Override
    @Transactional
    public void mergeCart(String userName,String guest){
        Cart userCart=cartRepository.findByUserName(userName);
        if(userCart==null){
            userCart=new Cart();
            userCart.setCartItemList(new ArrayList<>());
        }
        List<CartItem> cartItems=userCart.getCartItemList();
        Cart guestCart=cartRepository.findByUserName(guest);
        cartItems.addAll(guestCart.getCartItemList());
        cartRepository.delete(guestCart);
        userCart.setCartItemList(cartItems);
        cartRepository.save(userCart);
    }
}
