package com.example.sakankom;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class LoginFeatureSteps {
    private User user;
    boolean userValid , userTypeDetermined;
    static ArrayList<Apartment> apartments ;
    public LoginFeatureSteps(User user) {
        super();
        this.user = user;
        userValid = false;
        userTypeDetermined = false;


    }
    @BeforeAll
    public static void before_or_after_all(){
        apartments = new ArrayList<Apartment>();
        ResultSet rst;
        try {

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            //jdbc:oracle:thin:@//localhost:1521/xepdb1
            Statement st = con.createStatement();
            rst = st.executeQuery("select * from house,owner,residence where house.residence_id = residence.residence_id and residence.owner_id = owner.owner_id and house.isvalid = '1' and house.isaccepted = '1'");

            Apartment apt;
            while (rst.next()) {
                apt = new Apartment();
                apt.setOwnerEmail(rst.getString("email"));
                apt.setOwnerPhone(rst.getString("phone_number"));
                apt.setAddress(rst.getString("location"));
                apt.setOwnerName(rst.getString("fname") + " " + rst.getString("lname"));
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
                apt.setIsValid(rst.getString("isvalid"));
                apt.setIsAccepted(rst.getString("isaccepted"));
                apt.setIsReserved(rst.getString("isreserved"));
                apartments.add(apt);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] ff = {"222","11"};
        Wrapper.main(ff);
    }

    //The cases..
    @Given("the user is not logged in")
    public void theUserIsNotLoggedIn() {
        user.setFlag(false);
        assertFalse(user.getFlag());
    }
    @Given("the username is equal to <username> and the username is equal to <password>")
    public void theUsernameIsEqualToUsernameAndTheUsernameIsEqualToPassword(DataTable dataTable) {
        List<List<String>> myList = dataTable.asLists();
        ArrayList<User> values = Wrapper.signInHandler.getValues();

        for(int i = 1;i< myList.size();i++) {
            for (User value : values) {
                user.setUsername(value.getUsername());
                user.setPassword(value.getPassword());
                if (myList.get(i).get(0).equals(user.getUsername()) && myList.get(i).get(1).equals(user.getPassword())) {
                    userValid = true;
                    break;
                }

            }
            if (userValid)  break;
        }

        assertTrue(userValid);
    }

    @Then("the user should log in to the system")
    public void theUserShouldLogInToTheSystem() {
        assertTrue(Wrapper.signInHandler.isUserLoggedIn());
    }
    @Then("the user type is determined")
    public void theUserTypeIsDetermined() {
        for (int i = 0; i< Wrapper.signInHandler.getUsers().size(); i++) {
            if (Wrapper.signInHandler.getUsers().get(i).getUsername().equals(user.getUsername())) {
                user.setUserType(Wrapper.signInHandler.getUsers().get(i).getUserType());
                userTypeDetermined = true;
                break;
            }

        }
        assertTrue(userTypeDetermined);
    }

    //second scenario --------------------------------------------
    @Given("user not logged in")
    public void userNotLoggedIn() {
        user.setFlag(false);
        assertFalse(user.getFlag());
    }
    @Given("the password is not equal to <username> or the username is not equal to <password>")
    public void thePasswordIsNotEqualToUsernameOrTheUsernameIsNotEqualToPassword(DataTable dataTable) {
        boolean userValid2 = true;
        List<List<String>> myList = dataTable.asLists();
        ArrayList<User> values = Wrapper.signInHandler.getValues();

        for(int i = 1;i< myList.size();i++) {
            for (User value : values) {
                user.setUsername(value.getUsername());
                user.setPassword(value.getPassword());
                if (!myList.get(i).get(0).equals(user.getUsername()) && !myList.get(i).get(1).equals(user.getPassword())) {
                    userValid2 = false;
                    break;
                }

            }
            if (!userValid2)  break;
        }

        assertFalse(userValid2);
    }
    @Then("show a message indicating that the entered data is false")
    public void showAMessageIndicatingThatTheEnteredDataIsFalse() {
        if(Wrapper.signInHandler.isAlertShown()) assertTrue(true);
        else assertTrue(Wrapper.signInHandler.isLoginPageClosed());
    }

    //3rd Scenario
    @Given("the user presses on logout")
    public void theUserPressesOnLogout() {
        assertTrue(Wrapper.signInHandler.getMainPageHandler().isLogoutPressed() || Wrapper.signInHandler.getAdminMainPageHandler(). isLogoutBtnPressed());
    }
    @Then("the user should be logged out of the system")
    public void theUserShouldBeLoggedOutOfTheSystem() {
        assertTrue(Wrapper.signInHandler.getMainPageHandler().isLoggedOut() || Wrapper.signInHandler.getAdminMainPageHandler(). isLogoutBtnPressed());
    }

}
