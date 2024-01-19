package com.example.sakankom;

public class Admin {
    private int adminID;
    private String fName;

    public Admin() {

    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String lName;

    public Admin(int adminID, String fName, String lName, String username, String password) {
        this.adminID = adminID;
        this.fName = fName;
        this.lName = lName;
        this.username = username;
        this.password = password;
    }

    private String username;
    private String password;


}
