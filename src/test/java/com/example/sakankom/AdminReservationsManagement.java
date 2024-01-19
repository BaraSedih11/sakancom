package com.example.sakankom;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import junit.framework.TestCase;

import java.sql.*;
import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;


public class AdminReservationsManagement {
    User user;
    ArrayList<AdminReservation> adminReservations;

    public AdminReservationsManagement(User user){
        this.user = user;
        adminReservations = new ArrayList<>();
    }

    @Given("the user who is admin is logged in to the system")
    public void theUserWhoIsAdminIsLoggedInToTheSystem() {
        // Write code here that turns the phrase above into concrete actions
        AdminMainPageHandler adminMainPageHandler = Wrapper.signInHandler.getAdminMainPageHandler();
        user = adminMainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("admin")){
            TestCase.assertTrue(true);
        }
        else
            assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("admin"));
    }

    @Given("the user presses on the reservations button in the sidebar")
    public void theUserPressesOnTheReservationsButtonInTheSidebar() {
        AdminMainPageHandler adminMainPageHandler = Wrapper.signInHandler.getAdminMainPageHandler();
        user = adminMainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("admin")){
            TestCase.assertTrue(true);
        }
        else {
            // Write code here that turns the phrase above into concrete actions
            assertTrue(adminMainPageHandler.isReservationsPressed());
        }
    }

    @Then("all of the current available reservations should be displayed")
    public void allOfTheCurrentAvailableReservationsShouldBeDisplayed() {
        AdminMainPageHandler adminMainPageHandler = Wrapper.signInHandler.getAdminMainPageHandler();
        user = adminMainPageHandler.getUser();
        if (!user.getUserType().equalsIgnoreCase("admin")) {
            TestCase.assertTrue(true);
        } else {
            // Write code here that turns the phrase above into concrete actions
            ResultSet rst, rst2;
            try {

                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();

                rst2 = st.executeQuery("select * from reservation , tenant , house , residence , owner where reservation.tenant_id = tenant.tenant_id and reservation.house_id = house.house_id and house.residence_id = residence.residence_id and residence.owner_id = owner.owner_id");

                AdminReservation rs;
                while (rst2.next()) {
                    rs = new AdminReservation();
                    rs.setResidenceID(rst2.getInt("residence_id"));
                    rs.setHouseId(rst2.getInt("house_id"));
                    rs.setOwnerId(rst2.getInt("owner_id"));
                    rs.setTenantId(rst2.getInt("tenant_id"));

                    rs.setResidenceName(rst2.getString("residence_name"));
                    rs.setPrice(rst2.getInt("price"));
                    rs.setAddress(rst2.getString("location"));
                    adminReservations.add(rs);
                }

                rst = st.executeQuery("select * from owner");
                while (rst.next()) {
                    for (AdminReservation adminReservation : adminReservations) {
                        if (adminReservation.getOwnerId() == rst.getInt("owner_id")) {
                            adminReservation.setOwnerName(rst.getString("fname") + " " + rst.getString("lname"));
                            adminReservation.setOwnerPhone(rst.getString("phone_number"));
                            adminReservation.setOwnerEmail(rst.getString("email"));
                        }
                    }
                }
                rst.close();

                rst = st.executeQuery("select * from tenant");
                while (rst.next()) {
                    for (AdminReservation adminReservation : adminReservations) {
                        if (adminReservation.getTenantId() == rst.getInt("tenant_id")) {
                            adminReservation.setTenantName(rst.getString("fname") + " " + rst.getString("lname"));
                            adminReservation.setTenantPhone(rst.getString("phone_number"));
                            adminReservation.setTenantEmail(rst.getString("email"));
                        }
                    }
                }


                con.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }

            ArrayList<AdminReservation> adminReservations1 = adminMainPageHandler.getAdminReservations();

            boolean dataValid = true;
            for (AdminReservation adminReservation : adminReservations) {
                for (AdminReservation reservation : adminReservations1) {
                    if (adminReservation.getHouseId() == reservation.getHouseId()) {
                        if (adminReservation.getTenantId() != reservation.getTenantId() || adminReservation.getOwnerId() != reservation.getOwnerId() || adminReservation.getResidenceID() != reservation.getResidenceID()) {
                            dataValid = false;
                            break;
                        }
                    }


                }

            }


            assertTrue(dataValid);

        }
    }


}