package com.example.messageclientfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 396);
        stage.setTitle("Lamps Chat Client");
        stage.setScene(scene);

        stage.setOnCloseRequest(e -> Client.closeEverything());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}