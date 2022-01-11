module com.example.gorilla {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gorilla to javafx.fxml;
    exports com.example.gorilla;
}