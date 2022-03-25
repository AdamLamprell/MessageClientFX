package com.example.messageclientfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button button_login;

    @FXML
    private Button button_sign_up;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField pf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Client.connect("localhost");

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!tf_username.getText().trim().isEmpty() && !pf_password.getText().trim().isEmpty()) {
                    FXUtils.loginUser(actionEvent, tf_username.getText(), pf_password.getText());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Missing Username or Password!");
                    alert.show();
                }
            }
        });

        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXUtils.changeScene(actionEvent, "sign-up.fxml", "Sign up!", null);
            }
        });
    }
}