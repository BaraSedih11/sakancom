package com.example.sakankom;

import com.example.sakankom.SignInHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Wrapper extends  Application {
    public static SignInHandler signInHandler;
    public static FXMLLoader loader;
    @Override
    public void start(Stage stage) throws IOException {
        loader = new FXMLLoader(getClass().getResource("Sign-In.fxml"));
        Parent root = loader.load();
        signInHandler = loader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("Sakancom");
        stage.setScene(scene);
        stage.show();



    }


    public static void main(String[] args) {
        launch();
        signInHandler = loader.getController();

    }

}
