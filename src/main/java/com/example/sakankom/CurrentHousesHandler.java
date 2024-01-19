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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CurrentHousesHandler implements Initializable {
    private static final String TENANTHOUSES = "TenantHouses.css";
    private static final String FONTSIZE = "-fx-font-size: %.2fpt;";
    @FXML
    public VBox container;
    @FXML
    private MFXScrollPane mainPane;
    public ArrayList<Apartment> getApartments() {
        return apartments;
    }
    ArrayList<Apartment> apartments;
    ArrayList<Neigbour> neigbours;
    Tenant tenant;
    private boolean detailsPressed;
    private boolean reservePressed;
    HouseDetailsHandler houseDetailsHandler;
    Apartment myApartment;

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
        generateGUI();
    }

    public MFXScrollPane getMainPane() {
        return mainPane;
    }
    VBox card ;
    Label l1;
    Label l2;
    HBox cont;
    HBox cont2;
    Label l3;
    Label l4;
    Label l5;
    MFXButton reserveBtn;
    MFXButton detailsBtn;

    private void generateGUI (){
        //Declaring elements --------------------------------------------------------------------
        container.setMaxWidth(920);

        //prepare strings and data.
        String name;
        String owner;
        String type;
        String address;
        String price;


        for (Apartment apartment : apartments) {
            boolean checkTenant = false;
            for (Neigbour neigbour : neigbours) {
                if (neigbour.getTenantID() == tenant.getTenantID() && neigbour.getHouseID() == apartment.getHouseId()) {
                    checkTenant = true;
                    break;
                }
            }
            if (checkTenant)
                continue;

            if (apartment.getIsValid().equals("1") && apartment.getIsAccepted().equals("1") && apartment.getIsReserved().equalsIgnoreCase("0")) {
                //data
                name = apartment.getAptName();
                owner = apartment.getOwnerName();
                type = (apartment.getCapacity() > 1) ? "shared" : "solo";
                address = apartment.getAddress();
                price = Double.toString(apartment.getPrice());

                //generating elements
                //where it starts

                card = new VBox();
                card.setMaxWidth(950);
                card.getStylesheets().add(TENANTHOUSES);
                card.getStyleClass().add("vbox");

                l1 = new Label(name);
                l2 = new Label("By " + owner);
                l3 = new Label("Type: " + type + " room   ,");
                l4 = new Label("   Address: " + address);
                cont = new HBox();
                cont2 = new HBox();

                cont.getChildren().add(l3);
                cont.getChildren().add(l4);

                l5 = new Label("Price: " + price + "$" + "\t\t");
                reserveBtn = new MFXButton("Reserve");
                detailsBtn = new MFXButton("details");


                //setting the styles
                reserveBtn.getStylesheets().add(TENANTHOUSES);
                reserveBtn.getStyleClass().add("myBtn");
                detailsBtn.getStylesheets().add(TENANTHOUSES);
                detailsBtn.getStyleClass().add("myBtn");
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
                card.getChildren().add(cont);

                cont2.getChildren().add(l5);
                cont2.getChildren().add(detailsBtn);
                cont2.getChildren().add(new Label("   "));
                cont2.getChildren().add(reserveBtn);
                card.getChildren().add(cont2);

                container.getChildren().add(card);

                reserveBtn.setId(Integer.toString(apartment.getHouseId()));
                detailsBtn.setId(Integer.toString(apartment.getHouseId()));

                detailsBtn.setOnAction(handler);
                reserveBtn.setOnAction(handler2);


                //where it ends

            }

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailsPressed = false; reservePressed = false;
        apartments = new ArrayList<>();
        neigbours = new ArrayList<>();
        //retrieve the data from database
        try {
            getData();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void getData() throws SQLException {
        ResultSet rst ;
        ResultSet rst2;
        Statement st = null;
        try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678")){

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            st = con.createStatement();
            rst = st.executeQuery("select * from house,owner,residence where house.residence_id = residence.residence_id and residence.owner_id = owner.owner_id and house.isvalid = '1' and house.isaccepted = '1'");

            Apartment apt;
            while(rst.next()) {
                apt = new Apartment();
                apt.setOwnerEmail(rst.getString("email"));
                apt.setOwnerPhone(rst.getString("phone_number"));
                apt.setAddress(rst.getString("location"));
                apt.setOwnerName(rst.getString("fname") + " " + rst.getString("lname"));
                apt.setAptName(rst.getString("residence_name"));
                apt.setHouseId(rst.getInt("house_id"));
                apt.setResidenceId(rst.getInt("residence_id"));
                apt.setOwnerId(rst.getInt("owner_id"));
                apt.setBathsN(rst.getInt("bathrooms_number"));
                apt.setBedsN(rst.getInt("bedrooms_number"));
                apt.setServices(rst.getString("services"));
                apt.setPrice(rst.getDouble("price"));
                apt.setFloor(rst.getInt("floor_number"));
                apt.setAptNumber(rst.getInt("flat_number"));
                apt.setCapacity(rst.getInt("capacity"));
                apt.setResCapacity(rst.getInt("reserved_capacity"));
                apt.setGender(rst.getString("genders"));
                apt.setBalcony(rst.getString("balcony"));
                apt.setIsValid(rst.getString("isvalid"));
                apt.setIsAccepted(rst.getString("isaccepted"));
                apt.setIsReserved(rst.getString("isreserved"));
                apartments.add(apt);
            }

            //retrieving the data of the neighbours from the database...
            rst2 = st.executeQuery("select * from tenant, reservation where reservation.tenant_id = tenant.tenant_id");

            Neigbour neigbour;
            while (rst2.next()) {
                neigbour = new Neigbour();
                neigbour.setHouseID(rst2.getInt("house_id"));
                neigbour.setJob(rst2.getString("job"));
                neigbour.setTenantID(rst2.getInt("tenant_id"));
                neigbour.setName(rst2.getString("fname") + " " + rst2.getString("lname"));

                neigbours.add(neigbour);
            }


        }
        catch (SQLException e){ e.printStackTrace(); }
        finally {
            assert st != null;
            st.close();
        }
    }
    EventHandler<ActionEvent> handler = event -> {
        MFXButton btn = (MFXButton) event.getSource();
        int i = Integer.parseInt(btn.getId());

        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("House-Details.fxml"));
        Parent root = null;

        try { root = loader.load(); } catch (IOException e) { e.printStackTrace(); }

        Scene scene = new Scene(root,800,600);
        newStage.setScene(scene);
        newStage.show();

        houseDetailsHandler = loader.getController();

        for(int k= 0;k<apartments.size();k++){
            if(apartments.get(k).getHouseId() == i){ houseDetailsHandler.valuesSetter(apartments.get(k),apartments,neigbours); }
        }
        detailsPressed = true;

    };
    EventHandler<ActionEvent> handler2 = event -> {
        MFXButton btn = (MFXButton) event.getSource();
        String id = btn.getId();

         myApartment = new Apartment();
        for (Apartment apartment : apartments) {
            if (Integer.toString(apartment.getHouseId()).equalsIgnoreCase(id)) { myApartment = apartment; }
        }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String currDate = formatter.format(date);
        try {

                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();

                st.executeUpdate("insert into reservation values ("+ myApartment.getHouseId() +" , " + tenant.getTenantID() +", " + myApartment.getPrice() +", to_date('" + currDate +  "' ,'yyyy-MM-dd') ,to_date('" +  currDate +  "' ,'yyyy-MM-dd') , '1' )" );
                Neigbour neigbour = new Neigbour();
                neigbour.setName(tenant.getFname() + " " + tenant.getLname());
                neigbour.setHouseID(myApartment.getHouseId());
                neigbour.setJob(tenant.getJob());
                neigbour.setTenantID(tenant.getTenantID());
                neigbours.add(neigbour);

            myApartment.setResCapacity(myApartment.getResCapacity() + 1);
                st.executeUpdate("update house set reserved_capacity ="+ myApartment.getResCapacity() +" where house_id = " + myApartment.getHouseId() );
                if(myApartment.getResCapacity() == myApartment.getCapacity()) {
                    myApartment.setIsReserved("1");
                    st.executeUpdate("update house set isreserved = '1' where house_id = " + myApartment.getHouseId() );
                }

                con.close();
            }
            catch (SQLException e) { e.printStackTrace(); }

            while(!container.getChildren().isEmpty())  container.getChildren().remove(0);

            generateGUI();
            reservePressed = true;
    };


    public boolean isDetailsPressed() {
        return detailsPressed;
    }

    public boolean isReservePressed() {
        return reservePressed;
    }
}
