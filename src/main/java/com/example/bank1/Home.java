package com.example.bank1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.bank1.HelloApplication.end;

public class Home {
    private Stage stage;
    private Parent root;
    private FXMLLoader Loader;
    @FXML
    Label name;
    @FXML
    Label balance;
    public String user;
    public void logout(ActionEvent e) throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LogOut");
        alert.setHeaderText("You're about to LogOut!");
        alert.setContentText("Are You Sure?");
        if(alert.showAndWait().get() == ButtonType.OK){
            ((Stage)((Button)e.getSource()).getScene().getWindow()).close();
            stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("MS LogIn");
            stage.getIcons().add(new Image("C:\\Users\\DELL\\Desktop\\java\\Programs\\bank1\\src\\main\\resources\\com\\example\\bank1\\img\\icon.png"));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(event -> {event.consume(); end(stage);});
        }
    }
    public void display(String name) throws Exception {
        user = name;
        this.name.setText(name);
        try {
            Connection connect = new DatabaseConnection().getConnection();
            Statement statement = connect.createStatement();
            ResultSet rs = null;
            String sql = "select balance from Login where username='" + user + "'";
            rs = statement.executeQuery(sql);
            rs.next();
            balance.setText("Balance : " + rs.getString(1) + " ₹");

            connect.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void withdraw(ActionEvent e) throws IOException {
        Loader = new FXMLLoader(getClass().getResource("withdraw.fxml"));
        root = Loader.load();
        Withdraw draw = Loader.getController();
        draw.username(user);
        Scene scene = new Scene(root);
        stage = ((Stage)((Node)e.getSource()).getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }
    public void deposit(ActionEvent e) throws IOException {
        Loader = new FXMLLoader(getClass().getResource("deposit.fxml"));
        root = Loader.load();
        Deposit dep = Loader.getController();
        dep.username(user);
        Scene scene = new Scene(root);
        stage = ((Stage)((Node)e.getSource()).getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }
    public void disp(ActionEvent e) throws Exception {
        Loader = new FXMLLoader(getClass().getResource("details.fxml"));
        root = Loader.load();
        Details det = Loader.getController();
        det.username(user);
        Scene scene = new Scene(root);
        stage = ((Stage)((Node)e.getSource()).getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }

    public void transfer(ActionEvent e) throws Exception {
        Loader = new FXMLLoader(getClass().getResource("transfer.fxml"));
        root = Loader.load();
        Transfer tr = Loader.getController();
        tr.username(user);
        Scene scene = new Scene(root);
        stage = ((Stage)((Node)e.getSource()).getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }

    public void history(ActionEvent e) throws Exception {
        Loader = new FXMLLoader(getClass().getResource("history.fxml"));
        root = Loader.load();
        History hr = Loader.getController();
        hr.username(user);
        Scene scene = new Scene(root);
        stage = ((Stage)((Node)e.getSource()).getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }

    public void holders(ActionEvent e) throws Exception{
        Loader = new FXMLLoader(getClass().getResource("holders.fxml"));
        root = Loader.load();
        Account_Holders hr = Loader.getController();
        hr.username(user);
        Scene scene = new Scene(root);
        stage = ((Stage)((Node)e.getSource()).getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }
}
