package com.example.distributorProduct.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "distributor")
public class Distributor {
    @Id
    private String did;
    private String distributorName;
    private String password;
    private float avgRating;
    private int unitsSold;
    private float revenue;
    private List<String> pids;
    private List<String> aids;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(int unitsSold) {
        this.unitsSold = unitsSold;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public List<String> getPids() {
        return pids;
    }

    public void setPids(List<String> pids) {
        this.pids = pids;
    }

    public List<String> getAids() {
        return aids;
    }

    public void setAids(List<String> aids) {
        this.aids = aids;
    }

    public boolean isEmpty() {
        if(this.distributorName.equals("") && this.password.equals(""))
            return true;
        return false;
    }
}
