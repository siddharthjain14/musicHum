package com.example.distributorProduct.services;

import com.example.distributorProduct.entity.Distributor;
import com.example.distributorProduct.entity.DistributorToken;

public interface DistributorTokenService {
    DistributorToken createToken(Distributor distributor);
    DistributorToken save(DistributorToken distributorToken);
}
