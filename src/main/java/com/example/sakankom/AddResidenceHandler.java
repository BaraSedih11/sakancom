package com.example.sakankom;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.*;

public class AddResidenceHandler {

    @FXML
    private MFXTextField locationField;

    @FXML
    private MFXTextField ownerID;

    public String getLocationField() {
        return locationField.getText();
    }

    public String getOwnerID() {
        return ownerID.getText();
    }

    public String getResidenceID() {
        return residenceID.getText();
    }

    public String getResidenceName() {
        return residenceName.getText();
    }

    @FXML
    private MFXTextField residenceID;

    @FXML
    private MFXTextField residenceName;

    private boolean isClicked = false;

    public boolean isClicked() {
        return isClicked;
    }

    @FXML
    void submitBtnHandler() {
        isClicked = true;

        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();

            ResultSet rst = st.executeQuery("INSERT INTO RESIDENCE VALUES ('" + residenceID.getText() + "','" + ownerID.getText() + "','" + locationField.getText() + "','" + residenceName.getText() + "','1')");
            rst.close();
            con.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Add Residence Successfully :)");

            alert.showAndWait();
        } catch (SQLException e) { e.printStackTrace(); }
    }

}