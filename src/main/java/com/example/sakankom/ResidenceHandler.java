package com.example.sakankom;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ResidenceHandler {
    @FXML
    private ResidencesHandler residencesHandler;

    @FXML
    private Label locationField;

    @FXML
    private Label ownerName;

    @FXML
    private Label residenceName;



    public void setDate(Residence residence){
        locationField.setText(residence.getLocation());
        ownerName.setText(residence.getOwnerName());
        residenceName.setText(residence.getResidenceName());
    }

}
