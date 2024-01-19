package com.example.sakankom;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminReservationsHandler implements Initializable {
    private static final String TENANTHOUSES = "TenantHouses.css";
    private static final String FONTSIZE = "-fx-font-size: %.2fpt;";

    @FXML
    private VBox container;
    @FXML
    private MFXScrollPane mainPane;

    User user;
    ArrayList<AdminReservation> adminReservations;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminReservations = new ArrayList<>();

    }
    public void generateGUI(){
        //Declaring elements --------------------------------------------------------------------
        container.setMaxWidth(920);
        VBox card ;
        Label l1;
        Label l2;
        HBox cont ;
        HBox cont2;
        Label l3;
        Label l4;
        Label l5;
        Label l6;
        Label l7;
        Label l8;
        Label l9;

        //prepare strings and data.
        String tenant;
        String owner;
        String price;
        String address;
        String resName;

        String temail;
        String tphone;
        String oemail;
        String ophone;


        for (AdminReservation adminReservation : adminReservations) {
            //data
            tenant = adminReservation.getTenantName();
            owner = adminReservation.getOwnerName();
            resName = adminReservation.getResidenceName();
            price = Integer.toString(adminReservation.getPrice());
            address = adminReservation.getAddress();
            temail = adminReservation.getTenantEmail();
            tphone = adminReservation.getTenantPhone();
            oemail = adminReservation.getOwnerEmail();
            ophone = adminReservation.getOwnerPhone();

            //generating elements

            card = new VBox();
            card.setMaxWidth(950);
            card.getStylesheets().add(TENANTHOUSES);
            card.getStyleClass().add("vbox");

            l1 = new Label(resName);
            l2 = new Label("Owner: " + owner);
            l3 = new Label("Tenant: " + tenant);
            l4 = new Label("Address: " + address);
            l5 = new Label("Price: " + price + "$");
            l6 = new Label("Owner-email: " + oemail);
            l7 = new Label("Owner-phone: " + ophone);
            l8 = new Label("Tenant-email: " + temail);
            l9 = new Label("Tenant-phone: " + tphone);


            cont = new HBox();
            cont2 = new HBox();

            //setting the styles
            l1.getStylesheets().add(TENANTHOUSES);
            l1.getStyleClass().add("l1");

            cont2.getStylesheets().add(TENANTHOUSES);
            cont2.getStyleClass().add("cont2");

            cont.getStylesheets().add(TENANTHOUSES);
            cont.getStyleClass().add("cont");


            DoubleProperty fontSize = new SimpleDoubleProperty(18);
            l1.styleProperty().bind(Bindings.format(FONTSIZE, fontSize));
            DoubleProperty fontSize2 = new SimpleDoubleProperty(12);
            l2.styleProperty().bind(Bindings.format(FONTSIZE, fontSize2));
            l3.styleProperty().bind(Bindings.format(FONTSIZE, fontSize2));
            l4.styleProperty().bind(Bindings.format(FONTSIZE, fontSize2));
            l5.styleProperty().bind(Bindings.format(FONTSIZE, fontSize2));


            //adding elements to card
            card.getChildren().add(l1);
            card.getChildren().add(l2);
            card.getChildren().add(l3);
            card.getChildren().add(l4);
            card.getChildren().add(l5);

            cont.getChildren().add(l6);
            cont.getChildren().add(new Label("     "));
            cont.getChildren().add(l7);
            cont2.getChildren().add(l8);
            cont2.getChildren().add(new Label("     "));
            cont2.getChildren().add(l9);

            card.getChildren().add(cont);
            card.getChildren().add(cont2);

            container.getChildren().add(card);

            //where it ends
        }

    }


    public MFXScrollPane getMainPane() {
        return mainPane;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAdminReservations(ArrayList<AdminReservation> adminReservations) {
        this.adminReservations = adminReservations;
        generateGUI();
    }
}