module com.example.bank1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.bank1 to javafx.fxml;
    exports com.example.bank1;
}