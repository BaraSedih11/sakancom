package com.example.sakankom;

public class Reservation {
    private int houseId;
    private int tenantId;
    private int price;
    private String payyingDate;
    private String reservationDate;
    private  String isValid;

    //setters


    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPayyingDate(String payyingDate) {
        this.payyingDate = payyingDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    //getters


    public int getHouseId() {
        return houseId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public int getPrice() {
        return price;
    }

    public String getPayyingDate() {
        return payyingDate;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getIsValid() {
        return isValid;
    }
}
