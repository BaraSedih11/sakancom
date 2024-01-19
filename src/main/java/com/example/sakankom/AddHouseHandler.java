package com.example.sakankom;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.*;

public class AddHouseHandler {
    @FXML
    private MFXTextField balaconyNumber;
    @FXML
    private MFXTextField bathroomsNumber;
    @FXML
    private MFXTextField bedroomsNumber;
    @FXML
    private MFXTextField capacity;
    @FXML
    private MFXTextField flatNumber;
    @FXML
    private MFXTextField floorNumber;
    @FXML
    private MFXTextField genders;
    public String getImageName() {
        return imageName.getText();
    }
    @FXML
    private MFXTextField imageName;
    public String getHouseID() {
        return houseID.getText();
    }
    @FXML
    private MFXTextField houseID;
    private boolean isClicked = false;

    public boolean isClicked() {
        return isClicked;
    }

    public int getPrice() { return Integer.parseInt(price.getText()); }
    @FXML
    private MFXTextField price;
    @FXML
    private MFXTextField services;
    @FXML
    private MFXTextField reservedCapacity;
    @FXML
    private MFXTextField residenceID;
    private String residenceName;
    public String getResidenceName(){
        ResultSet rst;

        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();

            rst = st.executeQuery("SELECT * FROM residence WHERE isvalid='1' and RESIDENCE_ID='" + residenceID.getText() + "'");
            rst.next();
            residenceName = rst.getString("residence_name");

            con.close();
        } catch (SQLException e) { e.printStackTrace(); }

        return residenceName;
    }

    @FXML
    void submitBtnHandler() {
        isClicked = true;

        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();

            ResultSet rst = st.executeQuery("INSERT INTO HOUSE (HOUSE_ID, RESIDENCE_ID, BEDROOMS_NUMBER, bathrooms_number, SERVICES, PRICE, FLOOR_NUMBER, FLAT_NUMBER, CAPACITY, RESERVED_CAPACITY, GENDERS, BALCONY, ISVALID, ISACCEPTED, ISRESERVED, IMAGE) VALUES ('" + houseID.getText() + "','" + residenceID.getText() + "','" + bedroomsNumber.getText() + "','" + bathroomsNumber.getText() + "','" + services.getText() + "','" + price.getText() + "','" + floorNumber.getText() + "','" + flatNumber.getText() + "','" + capacity.getText() + "','" + reservedCapacity.getText() + "','" + genders.getText() + "','" + balaconyNumber.getText() + "','1','0','0','" + imageName.getText() + "')");
            rst.close();
            con.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Add House Successfully :)");

            alert.showAndWait();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}