package com.example.sakankom;

public class AdminReservation {
    private int residenceID;
    private int tenantId;
    private int houseId;
    private int ownerId;
    private String tenantName;
    private String ownerName;
    private String ownerPhone;
    private  String ownerEmail;
    private String tenantPhone;
    private String tenantEmail;
    private int price;
    private String address;
    private String residenceName;

    public void setResidenceID(int residenceID) {
        this.residenceID = residenceID;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public void setTenantPhone(String tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName;
    }


    public int getResidenceID() {
        return residenceID;
    }

    public int getTenantId() {
        return tenantId;
    }

    public int getHouseId() {
        return houseId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getTenantPhone() {
        return tenantPhone;
    }

    public String getTenantEmail() {
        return tenantEmail;
    }

    public int getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public String getResidenceName() {
        return residenceName;
    }
}