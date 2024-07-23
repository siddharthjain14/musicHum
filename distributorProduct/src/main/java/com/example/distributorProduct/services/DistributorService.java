package com.example.distributorProduct.services;

import com.example.distributorProduct.entity.Distributor;
import com.example.distributorProduct.entity.DistributorLoginObject;
import com.example.distributorProduct.entity.DistributorToken;

import java.util.List;

public interface DistributorService {
    Distributor updateDistributor(Distributor distributor);
    Distributor findByDistributorId(String distributorId);
    Distributor findByDistributorNameAndPassword(String distributorName,String password);
    void register(Distributor distributor);
    DistributorToken login(DistributorLoginObject distributor);
    Distributor getDetails(String did);
    void logout(String did);
    void deleteById(String did);
    List<String> addProduct(String did,String pid,String type);
    List<String> removeProduct(String did,String pid,String type);
}
