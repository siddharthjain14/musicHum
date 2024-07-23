package com.musichum.Product.repository;

import com.musichum.Product.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.jnlp.PersistenceService;
import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    List<Product> findByPid(String pid);
    Product deleteByPid(String pid);
}
