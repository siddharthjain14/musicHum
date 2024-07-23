package com.example.cart.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;

@Document(collection = "inventory")
public class Inventory {
    @Id
    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public HashMap<String, List<Float>> getListOfDid() {
        return listOfDid;
    }

    public void setListOfDid(HashMap<String, List<Float>> listOfDid) {
        this.listOfDid = listOfDid;
    }

    private HashMap<String,List<Float>> listOfDid;

}
