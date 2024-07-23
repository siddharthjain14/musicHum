package com.example.distributorProduct.services.impl;

import com.example.distributorProduct.entity.Distributor;
import com.example.distributorProduct.entity.DistributorToken;
import com.example.distributorProduct.repository.DistributorTokenRepository;
import com.example.distributorProduct.services.DistributorTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;

@Service
public class DistributorTokenServiceImplementation implements DistributorTokenService {
    @Autowired
    DistributorTokenRepository distributorTokenRepository;

    public DistributorToken save(DistributorToken dis){
        return distributorTokenRepository.save(dis);
    }
    public DistributorToken createToken(Distributor distributor){
        String uuid = UUID.randomUUID().toString();
        DistributorToken distributorToken=new DistributorToken();
        distributorToken.setDid(distributor.getDid());
        distributorToken.setToken(uuid);
        return distributorTokenRepository.save(distributorToken);
    }
}
