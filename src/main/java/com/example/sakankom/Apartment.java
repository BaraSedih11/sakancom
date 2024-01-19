package com.example.sakankom;

public class Apartment implements Comparable<Apartment>{
    private int houseId;
    private int residenceId;
    private int ownerId;
    private int bedsN;
    private int bathsN;
    private String services;
    private String aptName;
    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;

    private double price;
    private int floor;
    private int aptNumber;
    private int capacity;
    private int resCapacity;
    private String gender;
    private String isValid;
    private String isAccepted;
    private String balcony;
    private String address;
    private  String isReserved;

    public void setIsReserved(String isReserved) {
        this.isReserved = isReserved;
    }

    public String getIsReserved() {
        return isReserved;
    }

    //setters
    public void setAddress(String address) {
        this.address = address;
    }

    public void setBalcony(String balcony) {
        this.balcony = balcony;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public void setResidenceId(int residenceId) {
        this.residenceId = residenceId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setBedsN(int bedsN) {
        this.bedsN = bedsN;
    }

    public void setBathsN(int bathsN) {
        this.bathsN = bathsN;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setAptNumber(int aptNumber) {
        this.aptNumber = aptNumber;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setResCapacity(int resCapacity) {
        this.resCapacity = resCapacity;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

    //getters ---------------------------------------
    public String getBalcony() {
        return balcony;
    }

    public String getAptName() {
        return aptName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getHouseId() {
        return houseId;
    }

    public int getResidenceId() {
        return residenceId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getBedsN() {
        return bedsN;
    }

    public int getBathsN() {
        return bathsN;
    }

    public String getServices() {
        return services;
    }

    public double getPrice() {
        return price;
    }

    public int getFloor() {
        return floor;
    }

    public int getAptNumber() {
        return aptNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getAddress() {
        return address;
    }

    public int getResCapacity() {
        return resCapacity;
    }

    public String getGender() {
        return gender;
    }

    public String getIsValid() {
        return isValid;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }
    @Override
    public int compareTo(Apartment o) {
        return Integer.compare(this.getHouseId(), o.getHouseId());
    }
}
