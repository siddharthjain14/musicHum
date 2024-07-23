package com.example.distributorProduct.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DistributorToken {
    @Id
    private String did;
    private String token;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
