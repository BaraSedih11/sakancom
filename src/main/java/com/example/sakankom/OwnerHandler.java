package com.example.sakankom;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class OwnerHandler implements Initializable {
    private static final String DONOTHING = "Do nothing";
    private static final String SELECTED = "selected";
    private static final String SELECTEDCLASS = ".selected";

    @FXML
    private AnchorPane mainPane;
    @FXML
    private MFXButton btnAddHouse;
    private boolean userClickedAddResidencesBtn = false;
    private boolean userClickedResidencesBtn = false;
    @FXML
    private Label ownerName;

    @FXML
    private MFXButton btnAddResidence;

    @FXML
    private MFXButton btnResidences;
    @FXML
    private MFXButton btnMain;

    public ResidencesHandler getResidencesHandler() {
        return residencesHandler;
    }

    public AddHouseHandler getAddHouseHandler() {
        return addHouseHandler;
    }

    public AddResidenceHandler getAddResidenceHandler() {
        return addResidenceHandler;
    }

    public List<House> getRecentlyAdded() {
        return recentlyAdded;
    }

    public List<House> getRecommended() {
        return recommended;
    }

    private ResidencesHandler residencesHandler;
    private AddHouseHandler addHouseHandler;
    private AddResidenceHandler addResidenceHandler;
    private static final Logger logger = Logger.getLogger(OwnerHandler.class.getName());

    @FXML
    private HBox cardLayout;
    private List<House> recentlyAdded;
    private List<House> recommended;

    private VBox mainBox;

    @FXML
    private VBox show;
    @FXML
    private GridPane houseContainer;
    User user;
    Owner owner;

    public Owner getOwner() {
        return owner;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        ownerName.setText(user.getFullName());

        ResultSet rst;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();
            rst = st.executeQuery("select * from owner where username = '" + user.getUsername() + "'");

            owner = new Owner();
            if (rst.next()) { owner.setOwnerId(rst.getInt("owner_id")); }
            con.close();
        } catch (SQLException e) { e.printStackTrace(); }

        try { mainBtnHandler(); }  catch (IOException e) { e.printStackTrace(); }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = new User();

        mainBox = new VBox();
        mainBox.getChildren().setAll(show.getChildren());
    }

    @FXML
    void mainBtnHandler() throws IOException {
        if (btnMain.getStyleClass().contains(SELECTED)) { logger.warning(DONOTHING); }
        else {
            recentlyAdded = new ArrayList<>();
            recommended = new ArrayList<>();
            int column = 1;
            int row = 1;
            ResultSet rst;
            ResultSet rst2;

            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();
                Statement st2 = con.createStatement();

                rst2 = st2.executeQuery("select residence_name, residence_id from residence where isVAlid='1' and owner_id='" + owner.getOwnerId() + "'");
                while (rst2.next()) {
                    rst = st.executeQuery("select house_id, residence_id, image, price from house where isValid='1' and residence_id='" + rst2.getInt("residence_id") + "'");
                    while (rst.next()) { recommended.add(new House("House " + rst.getString("house_id"), "/photos/" + rst.getString("image"), rst.getInt("price"), rst2.getString("residence_name"))); }
                }
                if (recommended.size() < 4) { recentlyAdded.addAll(recommended); }
                else {
                    for (int j = 0; j <= recommended.size() / 2; j++) { recentlyAdded.add(recommended.get(j)); }
                }

                cardLayout.getChildren().clear();
                for (House value : recentlyAdded) {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                    HBox cardBox = fxmlLoader.load();

                    CardHandler cardHandler = fxmlLoader.getController();
                    cardHandler.setDate(value);
                    cardLayout.getChildren().add(cardBox);
                }
                Collections.reverse(recommended);
                for (House house : recommended) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("house.fxml"));
                    VBox houseBox = fxmlLoader.load();
                    HouseHandler houseHandler = fxmlLoader.getController();
                    houseHandler.setDate(house);

                    if (column == 6) {
                        column = 0;
                        ++row;
                    }
                    houseContainer.add(houseBox, column++, row);
                    GridPane.setMargin(houseBox, new Insets(10));
                }

                MFXButton foundButton = (MFXButton) mainPane.lookup(SELECTEDCLASS);
                if (foundButton != null) {
                    if (foundButton.getStyleClass().equals(btnAddHouse.getStyleClass())) btnAddHouse.getStyleClass().remove(SELECTED);
                    if (foundButton.getStyleClass().equals(btnAddResidence.getStyleClass())) btnAddResidence.getStyleClass().remove(SELECTED);
                    if (foundButton.getStyleClass().equals(btnResidences.getStyleClass())) btnResidences.getStyleClass().remove(SELECTED);
                }


                show.getChildren().clear();
                show.getChildren().add(mainBox);
                btnMain.getStyleClass().add(SELECTED);

                con.close();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    @FXML
    void addHouseBtnHandler() throws IOException {
        if (btnAddHouse.getStyleClass().contains(SELECTED)) { logger.warning(DONOTHING); }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addHouse.fxml"));
            VBox showBox = fxmlLoader.load();

            addHouseHandler = fxmlLoader.getController();

            show.getChildren().clear();
            show.getChildren().add(showBox);

            MFXButton foundButton = (MFXButton) mainPane.lookup(SELECTEDCLASS);
            if (foundButton != null) {
                if (foundButton.getStyleClass().equals(btnMain.getStyleClass())) btnMain.getStyleClass().remove(SELECTED);
                if (foundButton.getStyleClass().equals(btnAddResidence.getStyleClass())) btnAddResidence.getStyleClass().remove(SELECTED);
                if (foundButton.getStyleClass().equals(btnResidences.getStyleClass())) btnResidences.getStyleClass().remove(SELECTED);
            }
            btnAddHouse.getStyleClass().add(SELECTED);
        }
    }

    @FXML
    void addResidenceBtnHandler() throws IOException {
        userClickedAddResidencesBtn = true;
        if (btnAddResidence.getStyleClass().contains(SELECTED)) { logger.warning(DONOTHING); }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addResidence.fxml"));
            VBox showBox = fxmlLoader.load();

            addResidenceHandler = fxmlLoader.getController();

            MFXButton foundButton = (MFXButton) mainPane.lookup(SELECTEDCLASS);
            if (foundButton != null) {
                if (foundButton.getStyleClass().equals(btnMain.getStyleClass())) btnMain.getStyleClass().remove(SELECTED);
                if (foundButton.getStyleClass().equals(btnAddHouse.getStyleClass())) btnAddHouse.getStyleClass().remove(SELECTED);
                if (foundButton.getStyleClass().equals(btnResidences.getStyleClass())) btnResidences.getStyleClass().remove(SELECTED);
            }


            show.getChildren().clear();
            show.getChildren().add(showBox);
            btnAddResidence.getStyleClass().add(SELECTED);
        }
    }

    @FXML
    void residencesBtnHandler() {
        userClickedResidencesBtn = true;
        if (btnResidences.getStyleClass().contains(SELECTED)) { logger.warning(DONOTHING); }
        else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Residences.fxml"));
                VBox showBox = fxmlLoader.load();

                residencesHandler = fxmlLoader.getController();
                residencesHandler.setData(owner);

                MFXButton foundButton = (MFXButton) mainPane.lookup(SELECTEDCLASS);
                if (foundButton != null) {
                    if (foundButton.getStyleClass().equals(btnAddHouse.getStyleClass())) btnAddHouse.getStyleClass().remove(SELECTED);
                    if (foundButton.getStyleClass().equals(btnAddResidence.getStyleClass())) btnAddResidence.getStyleClass().remove(SELECTED);
                    if (foundButton.getStyleClass().equals(btnMain.getStyleClass())) btnMain.getStyleClass().remove(SELECTED);
                }

                show.getChildren().clear();
                show.getChildren().add(showBox);
                btnResidences.getStyleClass().add(SELECTED);
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    @FXML
    void logoutBtnHandler() {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();

        Stage newStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Sign-In.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();

        } catch (IOException e) { e.printStackTrace(); }
    }

    public boolean isUserClickedAddResidencesBtn() {
        return userClickedAddResidencesBtn;
    }

    public boolean isUserClickedResidencesBtn() {
        return userClickedResidencesBtn;
    }
}