package com.example.sakankom;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.sql.*;
import java.util.*;

import static junit.framework.TestCase.assertTrue;

public class ViewOwnerResidencesFeatureSteps {

    User user;
    Owner owner;

    public ViewOwnerResidencesFeatureSteps(User user, Owner owner){
        this.user = user;
        this.owner = owner;
    }

    @Given("user clicked residences button")
    public void user_clicked_residences_button() {
        OwnerHandler ownerHandler = Wrapper.signInHandler.getOwnerHandler();
        user = ownerHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else{
            assertTrue(Wrapper.signInHandler.getOwnerHandler().isUserClickedResidencesBtn());

        }
    }

    @Then("his residences should be shown")
    public void his_residences_should_be_shown() {

        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else {
            OwnerHandler ownerHandler = Wrapper.signInHandler.getOwnerHandler();
            owner = ownerHandler.getOwner();

            List<Residence> residencesFromDataBase = new ArrayList<>();

            ResultSet rst;

            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();

                rst = st.executeQuery("SELECT * FROM residence WHERE isvalid='1' AND owner_id='" + owner.getOwnerId() + "'");

                while (rst.next()) {
                    residencesFromDataBase.add(new Residence(
                            rst.getString("residence_id"),
                            rst.getString("owner_id"),
                            rst.getString("location"),
                            rst.getString("residence_name")
                    ));
                }
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            ResidencesHandler residencesHandler = Wrapper.signInHandler.getOwnerHandler().getResidencesHandler();
            List<Residence> residences = new ArrayList<>(residencesHandler.getResidences());

            boolean equals = true;
            Collections.reverse(residences);
            for (int i = 0; i < residences.size(); i++) {
                if (!residencesFromDataBase.get(i).getResidenceID().equalsIgnoreCase(residences.get(i).getResidenceID())) {
                    equals = false;
                    break;
                }
            }

            assertTrue(equals);
        }
    }

    @Given("user in the residences pane")
    public void user_in_the_residences_pane() {
        OwnerHandler ownerHandler = Wrapper.signInHandler.getOwnerHandler();
        user = ownerHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else {
            assertTrue(Wrapper.signInHandler.getOwnerHandler().isUserClickedResidencesBtn());
        }
    }

    @Given("user clicked show houses button of one residence")
    public void user_clicked_show_houses_button_of_one_residence() {
        OwnerHandler ownerHandler = Wrapper.signInHandler.getOwnerHandler();
        user = ownerHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else
            assertTrue(Wrapper.signInHandler.getOwnerHandler().getResidencesHandler().isUserClickedShowHouses());
    }

    @Then("all available floors in this residence should be shown with the houses inside it")
    public void all_available_floors_in_this_residence_should_be_shown_with_the_houses_inside_it() {
        OwnerHandler ownerHandler = Wrapper.signInHandler.getOwnerHandler();
        user = ownerHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else{
        Map<Integer, ArrayList<House>> housesByFloorFromDataBase = new HashMap<>();
        String residenceID = Wrapper.signInHandler.getOwnerHandler().getResidencesHandler().getResidenceID();
        Map<Integer, ArrayList<House>> housesByFloor = Wrapper.signInHandler.getOwnerHandler().getResidencesHandler().getHousesByFloor();

        ResultSet rst;
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();

            rst = st.executeQuery("SELECT * FROM house WHERE isValid='1' and residence_id='" + residenceID + "' ORDER BY floor_number");
            while(rst.next()) {
                int floor = rst.getInt("floor_number");
                String houseId = rst.getString("house_id");

                if (!housesByFloorFromDataBase.containsKey(floor)) {
                    housesByFloorFromDataBase.put(floor, new ArrayList<>());
                }

                House house = new House(houseId, floor);
                housesByFloorFromDataBase.get(floor).add(house);
            }



            boolean isEqual = true;
            for(int key : housesByFloorFromDataBase.keySet()){
                housesByFloorFromDataBase.get(key).sort(Comparator.comparingInt(p -> Integer.parseInt(p.getHouseID())));
                housesByFloor.get(key).sort(Comparator.comparingInt(p -> Integer.parseInt(p.getHouseID())));

                for (int i = 0 ; i < housesByFloorFromDataBase.get(key).size() ; i++){
                    if( !housesByFloorFromDataBase.get(key).get(i).getHouseID().equalsIgnoreCase(housesByFloor.get(key).get(i).getHouseID())){
                        isEqual = false;
                        break;
                    }
                }
            }
            assertTrue(isEqual);
        con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }
    }

    //below tested done
    @Given("user in the houses of the residence pane")
    public void user_in_the_houses_of_the_residence_pane() {
        OwnerHandler ownerHandler = Wrapper.signInHandler.getOwnerHandler();
        user = ownerHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else
            assertTrue(Wrapper.signInHandler.getOwnerHandler().getResidencesHandler().isUserClickedShowHouses());
    }

    @Given("user clicked show more button of one house")
    public void user_clicked_show_more_button_of_one_house() {
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else
            assertTrue(Wrapper.signInHandler.getOwnerHandler().getResidencesHandler().isUserCLickedShowMore());
    }

    @Then("All information about this house should be shown")
    public void all_information_about_this_house_should_be_shown() {
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else {
            HouseEditHandler houseEditHandler = Wrapper.signInHandler.getOwnerHandler().getResidencesHandler().getHouseEditHandler();
            boolean equals = false;
            String houseID = Wrapper.signInHandler.getOwnerHandler().getResidencesHandler().getHouseIDForShowMoreTest();

            ResultSet rst;
            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();

                rst = st.executeQuery("SELECT * FROM house WHERE isvalid='1' and house_id='" + houseID + "'");
                rst.next();
                if (rst.getString("house_id").equalsIgnoreCase(houseEditHandler.getHouseID()) &&
                        rst.getInt("residence_id") == houseEditHandler.getResidenceID() &&
                        rst.getInt("bedrooms_number") == houseEditHandler.getBedroomsNumber() &&
                        rst.getInt("bathrooms_number") == houseEditHandler.getBathroomsNumber() &&
                        rst.getString("services").equalsIgnoreCase(houseEditHandler.getServices()) &&
                        rst.getInt("price") == houseEditHandler.getPrice() &&
                        rst.getInt("floor_number") == houseEditHandler.getFloorNumber() &&
                        rst.getInt("flat_number") == houseEditHandler.getFlatNumber() &&
                        rst.getInt("capacity") == houseEditHandler.getCapacity() &&
                        rst.getInt("reserved_capacity") == houseEditHandler.getReservedCapacity() &&
                        rst.getString("Genders").equalsIgnoreCase(houseEditHandler.getGenders()) &&
                        rst.getString("balcony").equalsIgnoreCase(houseEditHandler.getBalaconyNumber()) &&
                        rst.getString("isAccepted").equalsIgnoreCase(houseEditHandler.getIsAcceptedValue()) &&
                        rst.getString("isReserved").equalsIgnoreCase(houseEditHandler.getIsReservedValue()) &&
                        rst.getString("image").equalsIgnoreCase(houseEditHandler.getImageName())
                ) equals = true;

                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            assertTrue(equals);
        }
    }

    @Given("user in the house information pane and he updated some values of this house")
    public void user_in_the_house_information_pane_and_he_updated_some_values_of_this_house() {
        OwnerHandler ownerHandler = Wrapper.signInHandler.getOwnerHandler();
        user = ownerHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else
            assertTrue(Wrapper.signInHandler.getOwnerHandler().getResidencesHandler().isUserCLickedShowMore());
    }

    @Given("user clicked update button of the update house pane")
    public void user_clicked_update_button_of_the_update_house_pane() {
        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else
            assertTrue(Wrapper.signInHandler.getOwnerHandler().getResidencesHandler().getHouseEditHandler().isClicked());
    }

    @Then("The information of this house should be updated according to the data that the user puts")
    public void the_information_of_this_house_should_be_updated_according_to_the_data_that_the_user_puts() {

        if(!user.getUserType().equalsIgnoreCase("owner")){
            assertTrue(true);
        }
        else {
            HouseEditHandler houseEditHandler = Wrapper.signInHandler.getOwnerHandler().getResidencesHandler().getHouseEditHandler();
            ResultSet rst;

            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();

                rst = st.executeQuery("SELECT * FROM house WHERE isvalid='1' and " +
                        "house_id='" + houseEditHandler.getHouseID() + "' and " +
                        "residence_id='" + houseEditHandler.getResidenceID() + "' and " +
                        "bedrooms_number='" + houseEditHandler.getBedroomsNumber() + "' and " +
                        "bathrooms_number='" + houseEditHandler.getBathroomsNumber() + "' and " +
                        "services='" + houseEditHandler.getServices() + "' and " +
                        "price='" + houseEditHandler.getPrice() + "' and " +
                        "floor_number='" + houseEditHandler.getFloorNumber() + "' and " +
                        "flat_number='" + houseEditHandler.getFlatNumber() + "' and " +
                        "capacity='" + houseEditHandler.getCapacity() + "' and " +
                        "reserved_capacity='" + houseEditHandler.getReservedCapacity() + "' and " +
                        "genders='" + houseEditHandler.getGenders() + "' and " +
                        "balcony='" + houseEditHandler.getBalaconyNumber() + "' and " +
                        "isAccepted='" + houseEditHandler.getIsAcceptedValue() + "' and " +
                        "isReserved='" + houseEditHandler.getIsReservedValue() + "' and " +
                        "image='" + houseEditHandler.getImageName() + "'"
                );
                rst.next();
                boolean isExists = rst.getString("isValid").equalsIgnoreCase("1");

                con.close();

                assertTrue(isExists);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
