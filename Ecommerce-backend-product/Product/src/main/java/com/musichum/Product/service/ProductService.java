package com.musichum.Product.service;

import com.musichum.Product.entity.Product;
import com.musichum.Product.entity.UpdateCost;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    List<Product> findByPid(String pid);
    void deleteByPid(String pid);
    void rateProduct(String pid,float Rating);
    void updateLowestCost(UpdateCost updateCost);
    List<Product> findAll();
}
