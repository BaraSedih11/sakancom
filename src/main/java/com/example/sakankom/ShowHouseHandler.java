package com.example.sakankom;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShowHouseHandler {
    @FXML
    private ImageView houseImage;

    @FXML
    private Label houseName;

    @FXML
    private Label houseRes;


    public void setDate(House house){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(house.getImageSrc())));
        houseImage.setImage(image);
        houseName.setText(house.getName());
        houseRes.setText(house.getRes());
    }
}
