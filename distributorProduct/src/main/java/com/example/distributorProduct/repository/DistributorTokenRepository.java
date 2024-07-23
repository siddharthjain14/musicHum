package com.example.distributorProduct.repository;

import com.example.distributorProduct.entity.DistributorToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorTokenRepository extends CrudRepository<DistributorToken,String> {
    void deleteByDid(String did);
}
