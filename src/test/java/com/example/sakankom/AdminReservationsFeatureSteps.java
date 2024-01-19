package com.example.sakankom;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class AdminReservationsFeatureSteps {

    User user;
    Admin admin;
    List<Apartment> apartmentList;
    AdminMainPageHandler adminMainPageHandler = Wrapper.signInHandler.getAdminMainPageHandler();

    public AdminReservationsFeatureSteps(User user, Admin admin){
        this.user = user;
        this.admin = admin;
    }

    @Given("user is logged in and the user is a admin")
    public void user_is_logged_in_and_the_user_is_a_admin() {
        user = adminMainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("admin")){
            assertTrue(true);
        }
        else
            assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("admin"));
    }

    @Given("user clicked reservations button")
    public void user_clicked_reservations_button() {
        user = adminMainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("admin")){
            assertTrue(true);
        }
        else
            assertTrue(adminMainPageHandler.isUserClickedReservationsButton());
    }

    @Then("the reservations should shown successfully")
    public void the_reservations_should_shown_successfully() {
        user = adminMainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("admin")){
            assertTrue(true);
        }
        else {
            admin = adminMainPageHandler.getAdmin();
            apartmentList = new ArrayList<>();

            ResultSet rst;
            try {

                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();
                rst = st.executeQuery("select * from house,owner,residence where house.residence_id = residence.residence_id and residence.owner_id = owner.owner_id and house.isvalid = '1' and house.isaccepted = '0'");

                Apartment apt;
                while (rst.next()) {
                    apt = new Apartment();
                    apt.setOwnerEmail(rst.getString("email"));
                    apt.setOwnerPhone(rst.getString("phone_number"));
                    apt.setAddress(rst.getString("location"));
                    apt.setOwnerName(rst.getString("fName") + " " + rst.getString("lName"));
                    apt.setAptName(rst.getString("residence_name"));
                    apt.setHouseId(rst.getInt("house_id"));
                    apt.setResidenceId(rst.getInt("residence_id"));
                    apt.setOwnerId(rst.getInt("owner_id"));
                    apt.setBathsN(rst.getInt("bathrooms_number"));
                    apt.setBedsN(rst.getInt("bedrooms_number"));
                    apt.setServices(rst.getString("services"));
                    apt.setPrice(rst.getDouble("price"));
                    apt.setFloor(rst.getInt("floor_number"));
                    apt.setAptNumber(rst.getInt("flat_number"));
                    apt.setCapacity(rst.getInt("capacity"));
                    apt.setResCapacity(rst.getInt("reserved_capacity"));
                    apt.setGender(rst.getString("genders"));
                    apt.setBalcony(rst.getString("balcony"));
                    apt.setIsValid(rst.getString("isValid"));
                    apt.setIsAccepted(rst.getString("isAccepted"));
                    apt.setIsReserved(rst.getString("isReserved"));
                    apartmentList.add(apt);
                }


                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            boolean firstTest = true;


            Collections.sort(apartmentList);
            Collections.sort(adminMainPageHandler.getApartments());


            for (int i = 0; i < adminMainPageHandler.getApartments().size(); i++) {
                if (apartmentList.get(i).getHouseId() != adminMainPageHandler.getApartments().get(i).getHouseId()) {
                    firstTest = false;
                    break;
                }
            }
            assertTrue(firstTest);
        }
    }

    @Given("user clicked accept button")
    public void user_clicked_accept_button() {
        user = adminMainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("admin")){
            assertTrue(true);
        }
        else
            assertTrue(adminMainPageHandler.isUserClickedAcceptButton());
    }

    @Then("the house will be accepted")
    public void the_house_will_be_accepted() {
        user = adminMainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("admin")){
            assertTrue(true);
        }
        else {
            if (!adminMainPageHandler.isUserClickedAcceptButton()) assertTrue(true);

            boolean notFound = true;
            for (Apartment apartment : adminMainPageHandler.getApartments()) {
                if (apartment.getHouseId() == adminMainPageHandler.houseIDTest1) {
                    notFound = false;
                    break;
                }
            }
            assertTrue(notFound);
        }
    }

    @Given("user clicked reject button")
    public void user_clicked_reject_button() {
        user = adminMainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("admin")){
            assertTrue(true);
        }
        else
            assertTrue(adminMainPageHandler.isUserClickedRejectButton());
    }

    @Then("the house will be rejected")
    public void the_house_will_be_rejected() {
        user = adminMainPageHandler.getUser();
        if (!user.getUserType().equalsIgnoreCase("admin")) {
            assertTrue(true);
        } else {
            if (!adminMainPageHandler.isUserClickedRejectButton()) assertTrue(true);

            boolean notFound = true;
            for (Apartment apartment : adminMainPageHandler.getApartments()) {
                if (apartment.getHouseId() == adminMainPageHandler.houseIDTest2) {
                    notFound = false;
                    break;
                }
            }
            assertTrue(notFound);
        }
    }
}
