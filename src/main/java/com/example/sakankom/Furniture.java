package com.example.sakankom;

public class Furniture {
    private int tenantId;
    private int furnitureId;
    private int price;
    private String name;
    private String ownerName;
    private String description;
    private String isValid;
    private String isSold;

    //setters


    public String getIsSold() {
        return isSold;
    }

    public void setIsSold(String isSold) {
        this.isSold = isSold;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public void setFurnitureId(int furnitureId) {
        this.furnitureId = furnitureId;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    //getters

    public int getTenantId() {
        return tenantId;
    }

    public int getFurnitureId() {
        return furnitureId;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIsValid() {
        return isValid;
    }
}
