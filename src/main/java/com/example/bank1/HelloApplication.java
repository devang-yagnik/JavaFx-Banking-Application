package com.example.bank1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.Statement;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Connection connect = new DatabaseConnection().getConnection();
        Statement stmt = connect.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS Login (acc_no varchar(50) PRIMARY KEY, username varchar(50), password varchar(50), balance varchar(50), aadhar varchar(50), mobile varchar(50), email varchar(50), address varchar(50), date date, gender varchar(50), acc_type varchar(50));");
        connect.close();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MS LogIn");
        stage.getIcons().add(new Image("C:\\Users\\DELL\\Desktop\\java\\Programs\\bank1\\src\\main\\resources\\com\\example\\bank1\\img\\icon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(event -> {event.consume(); end(stage);});
    }

    public static void end(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to Exit!");
        alert.setContentText("Are You Sure?");
        if(alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}