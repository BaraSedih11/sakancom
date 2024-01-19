package com.example.sakankom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private final SignInHandler signInHandler = new SignInHandler();
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Sign-In.fxml")));
        Scene scene = new Scene(root);

        stage.setTitle("Sakancom");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            // Handle window close event (user closes the login window)
            signInHandler.setCloseLoginPage(true);
        });
    }

    public static void main(String[] args) {
        launch();

    }
}