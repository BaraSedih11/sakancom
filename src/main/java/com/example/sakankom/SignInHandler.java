package com.example.sakankom;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SignInHandler implements Initializable {
    private static final String USERNAME1 = "username";

    @FXML
    public MFXTextField password;
    @FXML
    private AnchorPane mainPane;
    @FXML
    public MFXTextField username;

    Stage newStage = new Stage();
    Stage currentStage = new Stage();
    ArrayList<User> users ;
    ArrayList<User> values;

    public MainPageHandler getMainPageHandler() {
        return mainPageHandler;
    }

    private MainPageHandler mainPageHandler;

    public OwnerHandler getOwnerHandler() {
        return ownerHandler;
    }

    private OwnerHandler ownerHandler;

    public AdminMainPageHandler getAdminMainPageHandler() {
        return adminMainPageHandler;
    }

    private AdminMainPageHandler adminMainPageHandler;

    public boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }

    private boolean isUserLoggedIn;
    private boolean alertShown;
    private boolean loginPageClosed = false;



    public ArrayList<User> getUsers(){
        return users;
    }

    public ArrayList<User> getValues() {
        return values;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isUserLoggedIn = false;
        alertShown = false;
        values = new ArrayList<>();
        users = new ArrayList<>();
        try {
            getData();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void getData() throws SQLException {
        Statement st= null;
        ResultSet rst;
        ResultSet rst2;
        ResultSet rst3;
        try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678")) {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            //jdbc:oracle:thin:@//localhost:1521/xepdb1
            st = con.createStatement();
            rst = st.executeQuery("select username, pass from tenant ");


            //bringing tenants
            while (rst.next()) {
                users.add(new User(rst.getString(USERNAME1), rst.getString("pass"), "tenant",false));
            }
            rst2 = st.executeQuery("select username, pass, fname, lname from owner ");
            //bringing owners
            while (rst2.next()) {

                users.add(new User( rst2.getString(USERNAME1),  rst2.getString("pass"),  "owner",  false,  rst2.getString("fname") + " " + rst2.getString("lname")));
            }
            rst3 = st.executeQuery("select username, pass from admin ");
            //bringing admins
            while (rst3.next()) {

                users.add(new User(rst3.getString(USERNAME1), rst3.getString("pass"), "admin",false));
            }


        }catch (SQLException e){ e.printStackTrace(); }
        finally {
            assert st != null;
            st.close();
        }
    }
    @FXML
    void validator() {
        String u = username.getText();
        String p = password.getText();

        User tmp = new User();
        tmp.setPassword(p);tmp.setUsername(u);
        values.add(tmp);

        for (User user : users) {
            if (user.getPassword().equals(p) && user.getUsername().equalsIgnoreCase(u)) {
                user.setFlag(true);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Main-Page.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    mainPageHandler = loader.getController();
                    mainPageHandler.setUser(user);


                    if (user.getUserType().equals("tenant")) {
                        newStage.setScene(scene);
                        newStage.show();
                        mainPageHandler.setTenantData(user);
                        mainPageHandler.manageReservations();
                    }
                } catch (IOException e) { e.printStackTrace(); }

                try {
                    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("Owner.fxml"));
                    Parent root = loader2.load();
                    Scene scene = new Scene(root);

                    ownerHandler = loader2.getController();
                    ownerHandler.setUser(user);
                    if (user.getUserType().equals("owner")) {
                        newStage.setScene(scene);
                        newStage.show();
                    }
                } catch (IOException e) { e.printStackTrace(); }

                try {
                    FXMLLoader loader4 = new FXMLLoader(getClass().getResource("Admin-MainPage.fxml"));
                    Parent root = loader4.load();
                    Scene scene = new Scene(root);

                    adminMainPageHandler = loader4.getController();
                    adminMainPageHandler.setUser(user);
                    if (user.getUserType().equals("admin")) {
                        newStage.setScene(scene);
                        newStage.show();
                    }
                } catch (IOException e) { e.printStackTrace(); }

                isUserLoggedIn = true;
                //closing the current stage
                currentStage = (Stage) mainPane.getScene().getWindow();
                currentStage.close();
            }

        }
        if (!isUserLoggedIn) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid data");
            alert.setHeaderText("Invalid input, check the inserted data and try again");
            alert.show();
            alertShown = true;
        }

    }

    public void setCloseLoginPage(boolean b) { loginPageClosed = b; }

    public boolean isAlertShown() {
        return alertShown;
    }

    public boolean isLoginPageClosed() {
        return loginPageClosed;
    }
}
