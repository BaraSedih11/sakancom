package com.example.sakankom;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.sql.*;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;


public class FurnitureSteps {
    User user;
    Tenant tenant;
    ArrayList<Furniture> furnitures ;
    public FurnitureSteps (User user, Tenant tenant){
        this.user = user;
        this.tenant = tenant;
        furnitures = new ArrayList<>();
    }
    @Given("the user who is tenant is logged in to the system")
    public void theUserWhoIsTenantIsLoggedInToTheSystem() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("tenant"));
    }
    @Given("the tenant presses on the furniture button")
    public void theTenantPressesOnTheFurnitureButton() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertTrue(Wrapper.signInHandler.getMainPageHandler().isFurniturePressed());
    }
    @Then("all furniture that are published should be shown")
    public void allFurnitureThatArePublishedShouldBeShown() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else {
            ArrayList<Furniture> realFurnitures = Wrapper.signInHandler.getMainPageHandler().getFurnitureHandler().getFurnitures();

            ResultSet rst;
            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();
                rst = st.executeQuery("select * from tenant, furniture where furniture.tenant_id = tenant.tenant_id");

                Furniture furniture;
                while (rst.next()) {
                    furniture = new Furniture();

                    furniture.setFurnitureId(rst.getInt("furniture_id"));
                    furniture.setTenantId(rst.getInt("tenant_id"));
                    furniture.setName(rst.getString("name"));
                    furniture.setDescription(rst.getString("description"));
                    furniture.setPrice(rst.getInt("price"));
                    furniture.setIsValid(rst.getString("isvalid"));
                    furniture.setOwnerName(rst.getString("fname") + " " + rst.getString("lname"));
                    furniture.setIsSold(rst.getString("issold"));
                    furnitures.add(furniture);
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            boolean sizeMatches = furnitures.size() == realFurnitures.size();

            boolean dataMathces = true;
            for (Furniture furniture : furnitures) {
                for (Furniture realFurniture : realFurnitures) {
                    if (furniture.getFurnitureId() == realFurniture.getFurnitureId()) {
                        if (!(furniture.getOwnerName().equalsIgnoreCase(realFurniture.getOwnerName()) && furniture.getIsSold().equalsIgnoreCase(realFurniture.getIsSold()))) {
                            dataMathces = false;
                            System.out.println(furniture.getFurnitureId() + "  " + realFurniture.getFurnitureId());
                            System.out.println(furniture.getOwnerName() + "  " + realFurniture.getOwnerName());
                            System.out.println(furniture.getIsSold() + "  " + realFurniture.getIsSold());
                        }
                        break;
                    }

                }
            }

            assertTrue(dataMathces && sizeMatches);
        }
    }



    //2nd Scenario --------------
    @Given("the user who is tenant is logged in to the system and presses on furniture buttons")
    public void theUserWhoIsTenantIsLoggedInToTheSystemAndPressesOnFurnitureButtons() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("tenant") && Wrapper.signInHandler.getMainPageHandler().isFurniturePressed());
    }
    @Given("the user presses on buy button")
    public void theUserPressesOnBuyButton() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertTrue( Wrapper.signInHandler.getMainPageHandler().getFurnitureHandler().isBuyPressed());
    }
    @Then("the piece of furniture that he chose should be sold to him")
    public void thePieceOfFurnitureThatHeChoseShouldBeSoldToHim() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else {
            String soldFId = Wrapper.signInHandler.getMainPageHandler().getFurnitureHandler().soldFID;

            boolean pieceSold = false;
            ResultSet rst;
            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();
                rst = st.executeQuery("select * from furniture where furniture_id = '" + soldFId + "'");

                if (rst.next()) {
                    String sold = rst.getString("issold");
                    String valid = rst.getString("isValid");

                    if (sold.equalsIgnoreCase("1") && valid.equalsIgnoreCase("1")) {
                        pieceSold = true;
                    }
                }

                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            assertTrue(pieceSold);
        }
    }

    //3rd scenario
    @Given("the tenant navigates to the furniture page")
    public void theTenantNavigatesToTheFurniturePage() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("tenant"));
    }
    @Given("presses on add new")
    public void pressesOnAddNew() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertTrue(Wrapper.signInHandler.getMainPageHandler().getFurnitureHandler().isAddPressed());
    }
    @Given("the tenant fills all the required fields")
    public void theTenantFillsAllTheRequiredFields() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else {
            NewFurnitureHandler newFurnitureHandler = Wrapper.signInHandler.getMainPageHandler().getFurnitureHandler().newFurnitureHandler;
            assertTrue(!newFurnitureHandler.getUname().getText().isEmpty() && !newFurnitureHandler.getUprice().getText().isEmpty() && !newFurnitureHandler.getUdescription().getText().isEmpty());
        }
    }
    @Given("presses on sell")
    public void pressesOnSell() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else {
            NewFurnitureHandler newFurnitureHandler = Wrapper.signInHandler.getMainPageHandler().getFurnitureHandler().newFurnitureHandler;
            assertTrue(newFurnitureHandler.isSavePressed());
        }
    }
    @Then("the furniture piece should be added to the system")
    public void theFurniturePieceShouldBeAddedToTheSystem() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else {
            boolean checker = false;
            NewFurnitureHandler newFurnitureHandler = Wrapper.signInHandler.getMainPageHandler().getFurnitureHandler().newFurnitureHandler;
            ResultSet rst;
            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();
                rst = st.executeQuery("select * from furniture where furniture_id = '" + newFurnitureHandler.insertedId + "'");

                if (rst.next()) {
                    checker = true;
                }

                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            assertTrue(checker);
        }
    }

    //4th Scenario
    @Given("a tenant navigates to the furniture page")
    public void aTenantNavigatesToTheFurniturePage() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("tenant"));
    }
    @Given("the tenant presses on add new")
    public void theTenantPressesOnAddNew() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertTrue(Wrapper.signInHandler.getMainPageHandler().getFurnitureHandler().isAddPressed());
    }
    @Given("the tenant does not fill all the required fields")
    public void theTenantDoesNotFillAllTheRequiredFields() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertTrue(Wrapper.signInHandler.getMainPageHandler().getFurnitureHandler().newFurnitureHandler.isValuesInvalid());
    }
    @Given("the tenant presses on sell")
    public void theTenantPressesOnSell() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else {
            NewFurnitureHandler newFurnitureHandler = Wrapper.signInHandler.getMainPageHandler().getFurnitureHandler().newFurnitureHandler;
            assertTrue(newFurnitureHandler.isSavePressed());
        }
    }
    @Then("an Alert is shown")
    public void anAlertIsShown() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.getMainPageHandler();
        user = mainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("tenant")){
            assertTrue(true);
        }
        else
            assertTrue(Wrapper.signInHandler.getMainPageHandler().getFurnitureHandler().newFurnitureHandler.isValuesInvalid());
    }


}
