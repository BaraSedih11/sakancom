package com.example.sakankom;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FurnitureHandler implements Initializable {
    private static final String TENANTHOUSES = "TenantHouses.css";
    private static final String FONTSIZE = "-fx-font-size: %.2fpt;";

        Tenant tenant;
    @FXML
        private VBox container;
        @FXML
        private MFXScrollPane mainPane;
        NewFurnitureHandler newFurnitureHandler;
        ArrayList<Furniture> furnitures;
        private boolean buyPressed;
        private boolean addPressed;
        String soldFID;

    public ArrayList<Furniture> getFurnitures() {
        return furnitures;
    }

    public MFXScrollPane getMainPane() {
        return mainPane;
    }
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            addPressed = false;
            soldFID = "";
            buyPressed = false;
            tenant = new Tenant();
            furnitures = new ArrayList<>();
            try {
                getData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            generateGUI();

         }
         public void getData() throws SQLException {
             ResultSet rst;
             Statement st = null;
             try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678")){
                 DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

                 st = con.createStatement();
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

             }
             catch (SQLException e) { e.printStackTrace(); }
             finally {
                 assert st != null;
                 st.close();
             }
         }
         public void generateGUI (){

            while(!container.getChildren().isEmpty()) { container.getChildren().remove(0); }

            //Declaring elements --------------------------------------------------------------------
             container.setMaxWidth(1030);

             //prepare strings and data.
             String name;
             String ownerName;
             String description;
             String price;
            VBox card;
            Label l1;
            Label l2;
            Label l3;
            Label l4;
            MFXButton btn;
             for (Furniture furniture : furnitures) {
                 if (furniture.getIsValid().equalsIgnoreCase("1") && furniture.getIsSold().equalsIgnoreCase("0")) {
                     //data
                     name = furniture.getName();
                     ownerName = furniture.getOwnerName();
                     price = furniture.getPrice() + "";
                     description = furniture.getDescription();

                     //generating elements
                     //where it starts

                     card = new VBox();
                     card.setMaxWidth(950);
                     card.getStylesheets().add(TENANTHOUSES);
                     card.getStyleClass().add("vbox");

                     l1 = new Label(name);
                     l2 = new Label("By " + ownerName);
                     l3 = new Label("Description: " + description);
                     l4 = new Label(price + "$");

                     btn = new MFXButton("buy");
                     //setting the styles

                     l1.getStylesheets().add(TENANTHOUSES);
                     l1.getStyleClass().add("l1");


                     DoubleProperty fontSize = new SimpleDoubleProperty(18);
                     l1.styleProperty().bind(Bindings.format(FONTSIZE, fontSize));
                     DoubleProperty fontSize2 = new SimpleDoubleProperty(12);
                     l2.styleProperty().bind(Bindings.format(FONTSIZE, fontSize2));
                     l3.styleProperty().bind(Bindings.format(FONTSIZE, fontSize2));
                     l4.styleProperty().bind(Bindings.format(FONTSIZE, fontSize2));
                     btn.getStylesheets().add(TENANTHOUSES);
                     btn.getStyleClass().add("myBtn");

                     //adding elements to card
                     card.getChildren().add(l1);
                     card.getChildren().add(l2);
                     card.getChildren().add(l3);
                     card.getChildren().add(l4);
                     card.getChildren().add(btn);
                     container.getChildren().add(card);

                     btn.setId(Integer.toString(furniture.getFurnitureId()));
                     btn.setOnAction(handler);
                     //where it ends

                 }

             }

         }

            EventHandler<ActionEvent> handler = event -> {
                MFXButton btn = (MFXButton) event.getSource();
                String id = btn.getId();
                soldFID = id;

                try {
                    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                    Statement st = con.createStatement();
                    st.executeUpdate("update furniture set issold = '1' where furniture_id = '" + id + "'");


                    con.close();
                }catch (SQLException e){ e.printStackTrace(); }

                for (Furniture furniture : furnitures) {
                    if (furniture.getFurnitureId() == Integer.parseInt(id)) { furniture.setIsSold("1"); }
                }

                generateGUI();
                buyPressed = true;
            };
        @FXML
        void addFurniture() {
            //loading my page
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("newFurniture.fxml"));
            Parent root2;
            try{
                 root2 = loader2.load();
                Scene scene = new Scene(root2);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

            }catch (IOException e) { e.printStackTrace(); }

            newFurnitureHandler  = loader2.getController();
            newFurnitureHandler.setTenant(tenant);
            newFurnitureHandler.setFurnitures(furnitures);
            newFurnitureHandler.setFurnitureHandler(this);

            addPressed = true;
        }


    public boolean isBuyPressed() {
        return buyPressed;
    }

    public boolean isAddPressed() {
        return addPressed;
    }
}
