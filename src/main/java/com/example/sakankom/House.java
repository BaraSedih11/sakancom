package com.example.sakankom;

public class House {
    public House(String name, String imageSrc, int price, String res, int floor) { this.name = name; this.imageSrc = imageSrc; this.price = price; this.res = res; this.floor = floor; }
    public House(String name, String imageSrc, int price, String res) { this.name = name; this.imageSrc = imageSrc; this.price = price; this.res = res; }
    public House(String houseID, int floor){ this.houseID = houseID; this.floor = floor; }
    public House() {}

    private String name;
    private String houseID;
    public String getID(){
        return name.split(" ")[1];
    }
    private String imageSrc;
    private int price;

    public int getFloor() {
        return floor;
    }

    private String res;
    private int floor;


    public String getRes() {
        return res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String getHouseID() {
        return houseID;
    }
}