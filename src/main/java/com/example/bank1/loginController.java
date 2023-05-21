package com.example.bank1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class loginController {
    @FXML
    TextField uname;
    String acc_no(){
        String acc="";
        DatabaseConnection con;
        Connection connect;
        Statement statement;
        try{
            connect = new DatabaseConnection().getConnection();
            statement = connect.createStatement();
        }
        catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Connection Failed.");
            alert.setContentText("Try Checking Your Internet Connection.");
            alert.setHeaderText("Couldn't connect to DataBase!");
            alert.showAndWait();
            return null;
        }
        ResultSet rs = null;
        try {
            String sql = "select MAX(acc_no) from Login";
            rs = statement.executeQuery(sql);
            rs.next();
            acc = rs.getString(1);
            if(acc == null)
                acc="9210371";
        }
        catch(Exception e){
            System.out.println("rs returned null!");
        }
        int temp = Integer.parseInt(acc);
        temp++;
        acc = String.valueOf(temp);
        return acc;
    }
    public String account = acc_no();
    @FXML
    PasswordField pswd;
    private Stage stage;
    private Parent root;

    public void login(ActionEvent e) throws Exception {
        Statement statement;
        ResultSet rs = null;
        try {
            Connection connect = new DatabaseConnection().getConnection();
            statement = connect.createStatement();
            String sql = "select * from Login where username='" + uname.getText() + "' and password='" + pswd.getText() + "'";
            rs = statement.executeQuery(sql);
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("LogIn Successful");
                alert.setContentText("LogIn Successful");
                alert.setHeaderText("You've LoggedIn");
                if(alert.showAndWait().get() == ButtonType.OK){
                    String file="home.fxml";
                    if(Objects.equals(uname.getText(), "admin"))
                        file = "admin_home.fxml";
                    FXMLLoader Loader = new FXMLLoader(getClass().getResource(file));
                    root = Loader.load();
                    Home cont = Loader.getController();
                    cont.display(uname.getText().toUpperCase());
                    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("LogIn Unsuccessful");
                alert.setContentText("LogIn Unsuccessful");
                alert.setHeaderText("Username or Password Incorrect");
                alert.showAndWait();
            }
            connect.close();
        }
        catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Connection Failed.");
            alert.setContentText("Try Checking Your Internet Connection.");
            alert.setHeaderText("Couldn't connect to DataBase!");
            alert.showAndWait();
        }
    }
    public void signup(ActionEvent e) throws IOException {
        Stage stg = new Stage();
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("signup.fxml"));
        root = Loader.load();
        Signup up = Loader.getController();
        up.disp(account);
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.setResizable(false);
        stg.show();
    }
}
