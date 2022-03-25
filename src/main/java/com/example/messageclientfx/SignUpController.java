package com.example.messageclientfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    Button button_sign_up;

    @FXML
    Button button_log_in;

    @FXML
    TextField tf_username;

    @FXML
    PasswordField pf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("USER: " + tf_username.getText().trim() + "- Password: " + pf_password.getText().trim());

                if (!tf_username.getText().trim().isEmpty() && !pf_password.getText().trim().isEmpty()) {
                    FXUtils.signUpUser(actionEvent, tf_username.getText(), pf_password.getText());
                } else {
                    System.out.println("Please fill out all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();
                }
            }
        });

        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Client.closeEverything();
                FXUtils.changeScene(actionEvent, "login.fxml", "Log in!", null);
            }
        });

    }
}
