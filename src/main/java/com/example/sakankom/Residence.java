package com.example.sakankom;

public class Residence {
    private final String residenceID;
    private String location;
    private final String residenceName;

    public String getOwnerName() {
        return ownerName;
    }

    private String ownerName;

    public Residence(String residenceID, String location, String residenceName, String ownerName) { this.residenceID = residenceID; this.location = location; this.residenceName = residenceName; this.ownerName = ownerName; }

    public Residence(String residenceID, String location, String residenceName) { this.residenceID = residenceID; this.location = location; this.residenceName = residenceName; }

    public String getResidenceID() {
        return residenceID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getResidenceName() {
        return residenceName;
    }

}
