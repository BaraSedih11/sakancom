package com.example.sakankom;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class ResidencesHandler{
    private static final String SAKANKOM = "sakankom";
    private static final String JDBX = "jdbc:oracle:thin:@//localhost:1521/xepdb1";
    private static final String PASS = "12345678";
    private static final String RESIDENCE_ID = "residence_id";
    private static final String FLOORNUMBER = "floor_number";

    public HouseEditHandler getHouseEditHandler() {
        return houseEditHandler;
    }

    private HouseEditHandler houseEditHandler;
    private String houseIDForShowMoreTest;
    @FXML
    private GridPane residenceContainer;

    public List<Residence> getResidences() {
        return residences;
    }

    private List<Residence> residences;

    public Map<Integer, ArrayList<House>> getHousesByFloor() {
        return housesByFloor;
    }

    private Map<Integer, ArrayList<House>> housesByFloor;
    @FXML
    private Label mainLabel;
    @FXML
    private VBox show;
    private int ownerId;
    private boolean userClickedShowHouses = false;
    private boolean userCLickedShowMore = false;
    private String residenceID;


    public void display(){
        residences = new ArrayList<>();
        int column = 1;
        int row = 1;
        ResultSet rst;
        ResultSet rst2;

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection(JDBX, SAKANKOM, PASS);
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();

            rst = st.executeQuery("select owner_id, residence_id, residence_name, location from residence where isValid='1' and owner_id='" + ownerId + "'");

            while (rst.next()) {
                rst2 = st2.executeQuery("SELECT fname, lname FROM owner WHERE owner_id='" + rst.getString("owner_id") + "'");
                rst2.next();
                residences.add(new Residence(rst.getString(RESIDENCE_ID), rst.getString("location"), rst.getString("residence_name"), rst2.getString("fname") + " " + rst2.getString("lname")));
            }
            Collections.reverse(residences);
            for (Residence residence : residences) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("residence.fxml"));
                VBox residenceBox = fxmlLoader.load();

                ResidenceHandler residenceHandler = fxmlLoader.getController();
                residenceHandler.setDate(residence);

                housesByFloor = new HashMap<>();

                for (Node node : residenceBox.getChildren()) {
                    if (node instanceof HBox) {
                        for (Node node2 : ((HBox) node).getChildren()) {
                            if(node2 instanceof MFXButton button) {
                                button.setOnAction(event -> {
                                    showHouses(residence.getResidenceName());
                                    userClickedShowHouses = true;
                                    residenceID = residence.getResidenceID();

                                    ResultSet resultSet;
                                    try {
                                        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                                        Connection con1 = DriverManager.getConnection(JDBX, SAKANKOM, PASS);
                                        Statement st3 = con1.createStatement();

                                        resultSet = st3.executeQuery("SELECT * FROM house WHERE isvalid='1' and residence_id='" + residence.getResidenceID() + "'");
                                        while(resultSet.next()){
                                            int floor = resultSet.getInt(FLOORNUMBER);

                                            if (!housesByFloor.containsKey(floor)) { housesByFloor.put(floor, new ArrayList<>()); }

                                            House house = new House(resultSet.getString("house_id"), floor);
                                            housesByFloor.get(floor).add(house);
                                        }
                                        con1.close();
                                } catch (SQLException e) { e.printStackTrace(); }
                                });
                            }
                        }
                    }
                }

                if (column == 6) {
                    column = 0;
                    ++row;
                }
                residenceContainer.add(residenceBox, column++, row);
                GridPane.setMargin(residenceBox, new Insets(10));
            }

            con.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void showHouses(String residenceName){
        residenceContainer.getChildren().clear();
        List<House> houses = new ArrayList<>();
        int totalTenants = 0;
        ResultSet rst;
        ResultSet rst2;

        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection(JDBX, SAKANKOM, PASS);
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();

            rst = st.executeQuery("SELECT residence_id FROM residence WHERE residence_name='" + residenceName + "' and isvalid='1'");
            rst.next();
            rst2 = st2.executeQuery("SELECT * FROM house WHERE residence_id='" + rst.getString(RESIDENCE_ID) + "' and isvalid='1'");

            while (rst2.next()){
                houses.add(new House("House " + rst2.getString("house_id"), "/photos/" + rst2.getString("image"), rst2.getInt("price"), residenceName, rst2.getInt(FLOORNUMBER)));
                 totalTenants += rst2.getInt("reserved_capacity");
            }
        } catch (SQLException e) { e.printStackTrace(); }

        mainLabel.setText("All houses of " + residenceName + " . " + houses.size() + " Houses with " + totalTenants + " person reserved.");

        try{
            ResultSet rst3;
            ResultSet rst4;
            Set<Integer> uniqueFloors = new HashSet<>();
            int row = 1;

                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection(JDBX, SAKANKOM, PASS);
                Statement st = con.createStatement();
                Statement st2 = con.createStatement();

                rst3 = st2.executeQuery("SELECT residence_id FROM residence WHERE isvalid='1' and residence_name='" + residenceName + "'");
                rst3.next();
                rst4 = st.executeQuery("SELECT floor_number FROM house WHERE isvalid='1' and residence_id='" + rst3.getString(RESIDENCE_ID) + "'");

                while (rst4.next()) { uniqueFloors.add(rst4.getInt(FLOORNUMBER)); }

                Integer[] floorsArray = uniqueFloors.toArray(new Integer[0]);
                Arrays.sort(floorsArray);

                for (Integer integer : floorsArray) {
                    List<House> floorHouses = new ArrayList<>();
                    for (House housee : houses) {
                        if (housee.getFloor() == integer) { floorHouses.add(housee); }
                    }

                    FXMLLoader fxmlLoader1 = new FXMLLoader();
                    fxmlLoader1.setLocation(getClass().getResource("showHouses.fxml"));
                    VBox floor = fxmlLoader1.load();
                    ShowHousesHandler handler2 = fxmlLoader1.getController();
                    handler2.setDate(floorHouses);

                    residenceContainer.add(floor, 0, row++);
                    GridPane.setMargin(floor, new Insets(10));


                    for (Node node : floor.getChildren()) {
                        if (node instanceof MFXScrollPane scrollPane) {
                            Node content = scrollPane.getContent();
                            if (content instanceof HBox hbox) {
                                for (Node node1 : hbox.getChildren()) {
                                    if (node1 instanceof GridPane gridPane) {
                                        for (Node node2 : gridPane.getChildren()) {
                                            if (node2 instanceof VBox vBox) {
                                                VBox vBox2 = (VBox) vBox.lookup("#labels");
                                                Label label = (Label) vBox2.lookup("#houseName");
                                                MFXButton button = (MFXButton) vBox.lookup("#btnShow");
                                                button.setOnAction(actionEvent -> {
                                                    showMore(label.getText().split(" ")[1]);
                                                    userCLickedShowMore = true;
                                                });
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }


                    con.close();
                }


        } catch (SQLException e) { e.printStackTrace(); }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showMore(String houseID) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("houseEdit.fxml"));
            VBox vbox = fxmlLoader.load();

            houseEditHandler = fxmlLoader.getController();
            houseEditHandler.setDate(houseID);

            houseIDForShowMoreTest = houseID;

            show.getChildren().clear();
            show.getChildren().addAll(vbox.getChildren());

        }catch (IOException e){ e.printStackTrace(); }
    }

    public void setData(Owner owner){
        this.ownerId = owner.getOwnerId();
        this.display();
    }

    public String getHouseIDForShowMoreTest() {
        return houseIDForShowMoreTest;
    }

    public boolean isUserClickedShowHouses() {
        return userClickedShowHouses;
    }

    public boolean isUserCLickedShowMore() {
        return userCLickedShowMore;
    }

    public String getResidenceID() {
        return residenceID;
    }
}
