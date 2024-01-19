package com.example.sakankom;

public class Tenant {
    private int tenantID;
    private String fname;
    private String lname;
    private String bDate;
    private String pNumber;
    private String email;
    private String job;
    private String gender;
    private String username;
    private String password;

    //flags
    private boolean isUpdated;


    public Tenant() {
    //useless
    }

    //setters ---------------------------------------------------------
    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //getters ----------------------------------------------------------

    public boolean isUpdated() {
        return isUpdated;
    }

    public int getTenantID() {
        return tenantID;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getbDate() {
        return bDate;
    }

    public String getpNumber() {
        return pNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getJob() {
        return job;
    }

    public String getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
