package com.example.distributorProduct.entity;

public class DistributorLoginObject {
    private String distributorName;
    private String password;

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isEmpty() {
        if(this.distributorName.equals("") && this.password.equals(""))
            return true;
        return false;
    }
}
