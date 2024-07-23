package com.example.distributorProduct.repository;

import com.example.distributorProduct.entity.Distributor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributorRepository extends MongoRepository<Distributor,String> {
    List<Distributor> findByDistributorNameAndPassword(String distributorName, String password);
    Distributor findByDid(String distributorId);
}
