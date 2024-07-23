package com.musichum.Product.entity;

public class UpdateCost {
    String pid;
    int lowestCost;
    String lowestCostDID;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getLowestCost() {
        return lowestCost;
    }

    public void setLowestCost(int lowestCost) {
        this.lowestCost = lowestCost;
    }

    public String getLowestCostDID() {
        return lowestCostDID;
    }

    public void setLowestCostDID(String lowestCostDID) {
        this.lowestCostDID = lowestCostDID;
    }
}
