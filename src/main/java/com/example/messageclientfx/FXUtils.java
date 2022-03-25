package com.example.messageclientfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class FXUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;

        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(FXUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUsername(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(FXUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void loginUser(ActionEvent event, String username, String password) {
        System.out.println("Logging in user: " + username);
        String credentials = username + ":" + password + ":" + "0";

        if (!Client.loginUser(credentials)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Incorrect username or password!");
            alert.show();
            return;
        }

        FXUtils.changeScene(event, "logged-in.fxml", "Logged in!", username);

    }

    public static void signUpUser(ActionEvent event, String username, String password) {
        System.out.println("Signing up user: " + username);
        String credentials = username + ":" + password + ":" + "1";

        if (!Client.signUpUser(credentials)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Incorrect username or password!");
            alert.show();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("User: " + username + " has been created please sign in!");
        }

        FXUtils.changeScene(event, "login.fxml", "Login", null);
    }
}
