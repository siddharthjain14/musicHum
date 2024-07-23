package com.example.cart.repository;

import com.example.cart.entity.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory,String> {
    Inventory findByPid(String pid);
}
