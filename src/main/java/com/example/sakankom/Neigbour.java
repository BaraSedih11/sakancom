package com.example.sakankom;

public class Neigbour {
    private int tenantID;
    private String name;
    private String job;
    private int houseID;

    //setters

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    //getters


    public int getTenantID() {
        return tenantID;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;

    }

    public int getHouseID() {
        return houseID;
    }
}
