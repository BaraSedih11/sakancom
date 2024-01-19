package com.example.sakankom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowHousesHandler  {

    @FXML
    private GridPane floorContainer;

    @FXML
    private Label floorLabel;

    public void setDate(List<House> houses) throws IOException {

        floorLabel.setText("Floor " + houses.get(0).getFloor());
        int column = 1;
        int row = 1;
        for (House house : houses) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("showHouse.fxml"));
            VBox houseBox = fxmlLoader.load();

            ShowHouseHandler houseHandler = fxmlLoader.getController();
            houseHandler.setDate(house);

            if (column == 6) {
                column = 0;
                ++row;
            }
            floorContainer.add(houseBox, column++, row);
            GridPane.setMargin(houseBox, new Insets(10));
        }
    }

}
