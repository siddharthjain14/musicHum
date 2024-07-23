package com.example.cart.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CartWrapper")
public class CartWrapper {
    @Id
    private String pid;
    private String did;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }
}
