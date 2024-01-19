package com.example.sakankom;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.sql.*;

import static junit.framework.TestCase.assertTrue;

public class AddResidenceFeatureSteps {
    User user;
    Owner owner;

    public AddResidenceFeatureSteps(User user, Owner owner) {
        this.user = user;
        this.owner = owner;
    }


    @Given("user clicked addResidence button")
    public void user_clicked_add_residence_button() {
        OwnerHandler ownerHandler = Wrapper.signInHandler.getOwnerHandler();
        user = ownerHandler.getUser();
        owner = ownerHandler.getOwner();
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else
            assertTrue(ownerHandler.isUserClickedAddResidencesBtn());
    }

    @Given("user filled all fields with valid residence information and clicked add")
    public void user_filled_all_fields_with_valid_residence_information_and_clicked_add() {
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else {
            AddResidenceHandler addResidenceHandler = Wrapper.signInHandler.getOwnerHandler().getAddResidenceHandler();
            assertTrue(addResidenceHandler.isClicked());
        }
    }

    @Then("the residence should added successfully")
    public void the_residence_should_added_successfully() {
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else {
            AddResidenceHandler addResidenceHandler = Wrapper.signInHandler.getOwnerHandler().getAddResidenceHandler();

            Residence residence = new Residence(
                    addResidenceHandler.getResidenceID(),
                    addResidenceHandler.getOwnerID(),
                    addResidenceHandler.getLocationField(),
                    addResidenceHandler.getResidenceName()
            );

            ResultSet rst;
            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();

                rst = st.executeQuery("SELECT * FROM residence WHERE isValid='1' and owner_id='" + owner.getOwnerId() +
                        "' and residence_id='" + residence.getResidenceID() + "'");

                rst.next();
                boolean exists = rst.getString("residence_id").equals(residence.getResidenceID());
                assertTrue(exists);
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
