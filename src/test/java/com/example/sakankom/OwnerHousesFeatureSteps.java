package com.example.sakankom;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class OwnerHousesFeatureSteps {

    User user;
    Owner owner;

    public OwnerHousesFeatureSteps(User user, Owner owner){
        this.user = user;
        this.owner = owner;
    }

    @Given("user is logged in and the user is a owner")
    public void userIsLoggedInAndTheUserIsAOwner() {
        OwnerHandler ownerHandler = Wrapper.signInHandler.getOwnerHandler();
        user = ownerHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else
            assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("owner"));
    }

    @Then("his houses should be shown")
    public void hisHousesShouldBeShown() {
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else{
            OwnerHandler ownerHandler = Wrapper.signInHandler.getOwnerHandler();
            owner = ownerHandler.getOwner();

            List<House> recentlyAddedFromDataBase = new ArrayList<>();
            List<House> recommendedFromDataBase = new ArrayList<>();

            ResultSet rst, rst1;

            try{
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();
                Statement st1 = con.createStatement();

                rst = st.executeQuery("SELECT * FROM residence WHERE isValid = '1' and owner_id='" + owner.getOwnerId() + "'");

                while(rst.next()){
                    rst1 = st1.executeQuery("SELECT * FROM house WHERE isvalid='1' and residence_id='" + rst.getString("residence_id") + "'");
                    rst1.next();

                    recommendedFromDataBase.add(new House(
                            "House " + rst1.getString("house_id"),
                            "/photos/" + rst1.getString("image"),
                            rst1.getInt("price"),
                            rst.getString("residence_name")
                    ));
                }
                if (recommendedFromDataBase.size() < 4) {
                    recentlyAddedFromDataBase.addAll(recommendedFromDataBase);
                }
                else {
                    for (int j = 0; j <= recommendedFromDataBase.size() / 2; j++) {
                        recentlyAddedFromDataBase.add(recommendedFromDataBase.get(j));
                    }
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            boolean firstTest = true;

            Collections.reverse(ownerHandler.getRecommended());
            for (int i = 0 ; i < recommendedFromDataBase.size() ; i++){
                if(!recommendedFromDataBase.get(i).getName().equalsIgnoreCase(ownerHandler.getRecommended().get(i).getName())) firstTest = false;
                if(!recommendedFromDataBase.get(i).getID().equalsIgnoreCase(ownerHandler.getRecommended().get(i).getID())) firstTest = false;
                if(!recommendedFromDataBase.get(i).getRes().equalsIgnoreCase(ownerHandler.getRecommended().get(i).getRes())) firstTest = false;
            }
            Collections.reverse(recommendedFromDataBase);
            for (int i = 0 ; i < recentlyAddedFromDataBase.size() ; i++){
                if(!recentlyAddedFromDataBase.get(i).getName().equalsIgnoreCase(ownerHandler.getRecentlyAdded().get(i).getName())) firstTest = false;
                if(!recentlyAddedFromDataBase.get(i).getID().equalsIgnoreCase(ownerHandler.getRecentlyAdded().get(i).getID())) firstTest = false;
                if(!recentlyAddedFromDataBase.get(i).getRes().equalsIgnoreCase(ownerHandler.getRecentlyAdded().get(i).getRes())) firstTest = false;
            }
            assertTrue(firstTest);
        }

    }



    @Given("user clicked addHouse button and inserts into fields valid data and clicked add")
    public void userClickedAddHouseButtonAndInsertsIntoFieldsValidDataAndClickedAdd() {
        OwnerHandler ownerHandler = Wrapper.signInHandler.getOwnerHandler();
        user = ownerHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else {
            AddHouseHandler addHouseHandler = Wrapper.signInHandler.getOwnerHandler().getAddHouseHandler();
            assertTrue(addHouseHandler.isClicked());
        }
    }

    @Then("the house should be added to the database")
    public void theHouseShouldBeAddedToTheDatabase() {
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else {
            AddHouseHandler addHouseHandler = Wrapper.signInHandler.getOwnerHandler().getAddHouseHandler();

            ResultSet rst;

            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();

                House addedHouse = new House(
                        "House " + addHouseHandler.getHouseID(),
                        "/photos/" + addHouseHandler.getImageName(),
                        addHouseHandler.getPrice(),
                        addHouseHandler.getResidenceName()
                );


                rst = st.executeQuery("SELECT * FROM house WHERE isvalid='1' and house_id='" + addedHouse.getID() + "'");
                rst.next();

                String house_id;
                house_id = rst.getString("house_id");
                System.out.println(house_id);
                boolean exists = house_id != null;
                assertTrue(exists);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
