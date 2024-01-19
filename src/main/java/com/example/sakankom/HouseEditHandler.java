package com.example.sakankom;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.*;

public class HouseEditHandler   {
    private boolean isClicked = false;
    @FXML
    private MFXTextField balaconyNumber;

    @FXML
    private MFXTextField bathroomsNumber;

    @FXML
    private MFXTextField bedroomsNumber;

    public String getBalaconyNumber() {
        return balaconyNumber.getText();
    }

    public int getBathroomsNumber() {
        return Integer.parseInt(bathroomsNumber.getText());
    }

    public int getBedroomsNumber() {
        return Integer.parseInt(bedroomsNumber.getText());
    }

    public int getCapacity() {
        return Integer.parseInt(capacity.getText());
    }

    public int getFlatNumber() {
        return Integer.parseInt(flatNumber.getText());
    }

    public int getFloorNumber() {
        return Integer.parseInt(floorNumber.getText());
    }

    public String getGenders() {return genders.getText();}

    public String getHouseID() {
        return houseID.getText();
    }

    public String getImageName() {
        return imageName.getText();
    }

    public int getPrice() {
        return Integer.parseInt(price.getText());
    }

    public int getReservedCapacity() {
        return Integer.parseInt(reservedCapacity.getText());
    }

    public int getResidenceID() {
        return Integer.parseInt(residenceID.getText());
    }

    public String getServices() {
        return services.getText();
    }

    public String getIsAcceptedValue() {
        return isAcceptedValue;
    }

    public String getIsReservedValue() {
        return isReservedValue;
    }

    @FXML
    private MFXTextField capacity;

    @FXML
    private MFXTextField flatNumber;

    @FXML
    private MFXTextField floorNumber;

    @FXML
    private MFXTextField genders;

    @FXML
    private MFXTextField houseID;

    @FXML
    private MFXTextField imageName;

    @FXML
    private MFXTextField price;

    @FXML
    private MFXTextField reservedCapacity;

    @FXML
    private MFXTextField residenceID;

    @FXML
    private MFXTextField services;
    private String isAcceptedValue;
    private String isReservedValue;
    public void setDate(String houseName){
        ResultSet rst1;

        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();

            rst1 = st.executeQuery("SELECT * FROM house WHERE isValid='1' and house_id='" + houseName + "'");
            rst1.next();

            houseID.setText(rst1.getString("house_id"));
            bedroomsNumber.setText(rst1.getString("bedrooms_number"));
            residenceID.setText(rst1.getString("residence_id"));
            imageName.setText(rst1.getString("image"));
            floorNumber.setText(rst1.getString("floor_number"));
            flatNumber.setText(rst1.getString("flat_number"));
            bathroomsNumber.setText(rst1.getString("bathrooms_number"));
            balaconyNumber.setText(rst1.getString("balcony"));
            price.setText(rst1.getString("price"));
            capacity.setText(rst1.getString("capacity"));
            reservedCapacity.setText(rst1.getString("reserved_capacity"));
            genders.setText(rst1.getString("genders"));
            services.setText(rst1.getString("services"));
            isAcceptedValue = rst1.getString("isAccepted");
            isReservedValue = rst1.getString("isReserved");

            houseID.setEditable(false);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @FXML
    void updateBtnHandler() {
        isClicked = true;
        ResultSet rst1;

        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();

            rst1 = st.executeQuery("update house set HOUSE_ID='" + houseID.getText() + "'," + "RESIDENCE_ID='" + residenceID.getText() + "', BEDROOMS_NUMBER='" + bedroomsNumber.getText() + "', BATHROOMS_NUMBER='" + bathroomsNumber.getText() + "', SERVICES='" + services.getText() + "', PRICE='" + price.getText() + "', FLOOR_NUMBER='" + floorNumber.getText() + "', FLAT_NUMBER='" + flatNumber.getText() + "', CAPACITY='" + capacity.getText() + "', RESERVED_CAPACITY='" + reservedCapacity.getText() + "', GENDERS='" + genders.getText() + "', BALCONY='" + balaconyNumber.getText() + "', ISVALID='1', ISACCEPTED='" + isAcceptedValue + "', ISRESERVED='" + isReservedValue + "', IMAGE='" + imageName.getText() + "' WHERE house_id='" + houseID.getText() + "'");
            rst1.close();
            con.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Update House Successfully :)");

            alert.showAndWait();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public boolean isClicked() {
        return isClicked;
    }
}
