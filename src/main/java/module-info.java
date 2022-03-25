module com.example.messageclientfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.messageclientfx to javafx.fxml;
    exports com.example.messageclientfx;
}