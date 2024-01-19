package com.example.sakankom;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.sql.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TenantDataFeatureSteps {
    User user;
    Tenant tenant;
    public TenantDataFeatureSteps(User user,Tenant tenant) {
        this.user = user;
        this.tenant = tenant;
    }


    //The first scenario
    @Given("user is logged in and the user is a tenant")
    public void userIsLoggedInAndTheUserIsATenant() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("tenant"));
    }
    @Then("his personal data should be shown")
    public void hisPersonalDataShouldBeShown() {
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else {
            // Write code here that turns the phrase above into concrete actions
            tenant = mainPageHandler.getTenant();
            Tenant realTenant = new Tenant();

            boolean doesTenantExist = false;
            ResultSet rst;
            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                //jdbc:oracle:thin:@//localhost:1521/xepdb1
                Statement st = con.createStatement();
                rst = st.executeQuery("select * from tenant where username = '" + user.getUsername() + "'");

                if (rst.next()) {
                    doesTenantExist = true;
                    realTenant.setTenantID(rst.getInt("tenant_id"));
                    realTenant.setFname(rst.getString("fname"));
                    realTenant.setLname(rst.getString("lname"));
                    realTenant.setbDate(rst.getString("birthdate"));
                    realTenant.setpNumber(rst.getString("phone_number"));
                    realTenant.setEmail(rst.getString("email"));
                    realTenant.setJob(rst.getString("job"));
                    realTenant.setGender(rst.getString("gender"));
                    realTenant.setUsername(user.getUsername());
                    realTenant.setPassword(user.getPassword());
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //check if the data matches
            boolean dataMatches = tenant.getPassword().equalsIgnoreCase(realTenant.getPassword()) && tenant.getJob().equalsIgnoreCase(realTenant.getJob()) && tenant.getbDate().equalsIgnoreCase(realTenant.getbDate()) && tenant.getTenantID() == realTenant.getTenantID() && tenant.getpNumber().equalsIgnoreCase(realTenant.getpNumber()) && tenant.getEmail().equalsIgnoreCase(realTenant.getEmail()) && tenant.getUsername().equalsIgnoreCase(realTenant.getUsername());

            boolean fieldsMatch = mainPageHandler.getuEmail().getText().equalsIgnoreCase(realTenant.getEmail()) && mainPageHandler.getuGender().getText().equalsIgnoreCase(realTenant.getGender()) && mainPageHandler.getuJob().getText().equalsIgnoreCase(realTenant.getJob()) && mainPageHandler.getuBdate().getText().equalsIgnoreCase(realTenant.getbDate().substring(0, 10)) && mainPageHandler.getuUsername().getText().equalsIgnoreCase(realTenant.getUsername()) && mainPageHandler.getuPhone().getText().equalsIgnoreCase(realTenant.getpNumber());

            assertTrue(doesTenantExist && dataMatches && fieldsMatch);
            //assertTrue(true);
        }
    }


    //The second scenario
    @Given("the user who is tenant in the profile and presses edit")
    public void theUserWhoIsTenantInTheProfileAndPressesEdit() {
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){ assertTrue(true); }
        else assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("tenant") && mainPageHandler.isEditPressed());
    }
    @Given("user presses save after editing the data")
    public void userPressesSaveAfterEditingTheData() {
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){ assertTrue(true); }
        else assertTrue(mainPageHandler.isSavePressed());
    }
    @Then("his data should be updated")
    public void hisDataShouldBeUpdated() {
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else {
            tenant = mainPageHandler.getTenant();
            user = mainPageHandler.getUser();
            Tenant realTenant = new Tenant();

            ResultSet rst;
            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();
                rst = st.executeQuery("select * from tenant where username = '" + user.getUsername() + "'");

                if (rst.next()) {
                    realTenant.setTenantID(rst.getInt("tenant_id"));
                    realTenant.setFname(rst.getString("fname"));
                    realTenant.setLname(rst.getString("lname"));
                    realTenant.setbDate(rst.getString("birthdate"));
                    realTenant.setpNumber(rst.getString("phone_number"));
                    realTenant.setEmail(rst.getString("email"));
                    realTenant.setJob(rst.getString("job"));
                    realTenant.setGender(rst.getString("gender"));
                    realTenant.setUsername(user.getUsername());
                    realTenant.setPassword(user.getPassword());
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            boolean fieldsMatch = mainPageHandler.getuEmail().getText().equalsIgnoreCase(realTenant.getEmail()) && mainPageHandler.getuGender().getText().equalsIgnoreCase(realTenant.getGender()) && mainPageHandler.getuJob().getText().equalsIgnoreCase(realTenant.getJob()) && mainPageHandler.getuBdate().getText().equalsIgnoreCase(realTenant.getbDate().substring(0, 10)) && mainPageHandler.getuUsername().getText().equalsIgnoreCase(realTenant.getUsername()) && mainPageHandler.getuPhone().getText().equalsIgnoreCase(realTenant.getpNumber());

            assertTrue(fieldsMatch);
        }
    }
    //3rd Scenario
    @Given("the tenant has reservations")
    public void theTenantHasReservations() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertTrue(mainPageHandler.getReservations().size() > 0);
    }
    @Then("all of them should be displayed")
    public void allOfThemShouldBeDisplayed() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertEquals(mainPageHandler.container.getChildren().size(), mainPageHandler.getReservations().size());
    }


}
