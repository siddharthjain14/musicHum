package com.example.cart.repository;

import com.example.cart.entity.Cart;
import com.example.cart.entity.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends MongoRepository<Cart,String> {
    Cart findByUserName(String userName);
}
