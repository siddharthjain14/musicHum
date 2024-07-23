package com.example.distributorProduct.services.impl;

import com.example.distributorProduct.entity.Distributor;
import com.example.distributorProduct.entity.DistributorLoginObject;
import com.example.distributorProduct.entity.DistributorToken;
import com.example.distributorProduct.repository.DistributorRepository;
import com.example.distributorProduct.repository.DistributorTokenRepository;
import com.example.distributorProduct.services.DistributorService;
import com.example.distributorProduct.services.DistributorTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
public class DistributorServiceImplementation implements DistributorService {
    @Autowired
    DistributorRepository distributorRepository;
    @Autowired
    DistributorTokenService distributorTokenService;
    @Autowired
    DistributorTokenRepository distributorTokenRepository;


    public Distributor findByDistributorId(String distributorId){
        return distributorRepository.findByDid(distributorId);
    }

    @Override
    public Distributor updateDistributor(Distributor distributor) {
        Distributor dist=distributorRepository.findByDid(distributor.getDid());
        dist.setDistributorName(distributor.getDistributorName());
        dist.setPassword(distributor.getPassword());
        return distributorRepository.save(dist);
    }

    @Override
    public void register(Distributor distributor) {
        distributor.setAids(new ArrayList<String>());
        distributor.setPids(new ArrayList<String>());
        distributor.setAvgRating(0);
        distributor.setRevenue(0);
        distributor.setUnitsSold(0);
        distributorRepository.save(distributor);
    }

    @Override
    public DistributorToken login(DistributorLoginObject distributor) {
        Distributor d=distributorRepository.findByDistributorNameAndPassword(distributor.getDistributorName(),distributor.getPassword()).get(0);
        return distributorTokenService.createToken(d);
    }

    @Override
    public Distributor getDetails(String did) {
        return distributorRepository.findByDid(did);
    }

    @Override
    public void logout(String did) {
        distributorTokenRepository.deleteByDid(did);
    }

    @Override
    public void deleteById(String did) {
        distributorRepository.deleteById(did);
    }

    @Override
    public List<String> addProduct(String did,String pid, String type) {
        if(type.equals("Song")){
            Distributor distributor=distributorRepository.findByDid(did);
            List<String> products = distributor.getPids();
            products.add(pid);
            distributor.setPids(products);
            distributorRepository.save(distributor);
            return products;
        }
        else{
            Distributor distributor=distributorRepository.findByDid(did);
            List<String> products = distributor.getAids();
            products.add(pid);
            distributor.setPids(products);
            distributorRepository.save(distributor);
            return products;
        }
    }

    @Override
    public List<String> removeProduct(String did,String pid, String type) {
        if(type.equals("Song")){
            Distributor distributor=distributorRepository.findByDid(did);
            List<String> products = distributor.getPids();
            int index=0;
            for(int i=0;i<products.size();i++)
                if(products.get(i)==pid)
                    index=i;
            products.remove(index);
            distributor.setPids(products);
            distributorRepository.save(distributor);
            return products;
        }
        else{
            Distributor distributor=distributorRepository.findByDid(did);
            List<String> products = distributor.getAids();
            int index=0;
            for(int i=0;i<products.size();i++)
                if(products.get(i)==pid)
                    index=i;
            products.remove(index);
            distributor.setPids(products);
            distributorRepository.save(distributor);
            return products;
        }
    }

    public Distributor findByDistributorNameAndPassword(String distributorName, String password){
        return distributorRepository.findByDistributorNameAndPassword(distributorName,password).get(0);
    }


}
