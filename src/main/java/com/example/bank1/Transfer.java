package com.example.bank1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.TimeZone;

public class Transfer {
    @FXML
    TextField acc_no;
    @FXML
    TextField amt;
    private String user;
    private Parent root;
    private Stage stage;
    private FXMLLoader Loader;

    public void prev(MouseEvent e) throws Exception{
        String file="home.fxml";
        if(Objects.equals(user, "ADMIN"))
            file = "admin_home.fxml";
        FXMLLoader Loader = new FXMLLoader(getClass().getResource(file));
        root = Loader.load();
        Home hm = Loader.getController();
        hm.display(user);
        Scene scene = new Scene(root);
        stage = ((Stage)((Node)e.getSource()).getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }
    public void transfer(ActionEvent e) throws Exception {
        String account = acc_no.getText();
        double amount = Double.parseDouble(amt.getText());
        Connection connect = new DatabaseConnection().getConnection();
        Statement statement = connect.createStatement();
        ResultSet rs = null;
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String sql = "select * from Login where acc_no='"+account+"'";
            rs = statement.executeQuery(sql);
            if(!rs.next()){
                alert.setTitle("Invalid Account");
                alert.setHeaderText("No such Account found!");
                alert.setContentText("Please verify Account No.");
                alert.showAndWait();
                return;
            }
            sql = "select balance from Login where username='"+user+"'";
            rs = statement.executeQuery(sql);
            rs.next();
            double balance = Double.parseDouble(rs.getString(1));
            if(amount>balance){
                alert.setTitle("Error");
                alert.setHeaderText("Insufficient Balance");
                alert.setContentText("Deposit First");
                alert.showAndWait();
                FXMLLoader Loader = new FXMLLoader(getClass().getResource("home.fxml"));
                root = Loader.load();
                Home cont = Loader.getController();
                cont.display(user);
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                return;
            }
            sql = "update Login set balance = balance-"+amount+" where username='"+user+"'";
            alert.setTitle("Confirm");
            alert.setHeaderText("Confirm Transfer Of "+amount+" INR");
            alert.setContentText("Click Ok to proceed");
            if (alert.showAndWait().get() == ButtonType.CANCEL) {
                return;
            }
            statement.execute(sql);
            sql = "update Login set balance = balance+"+amount+" where acc_no='"+account+"'";
            statement.execute(sql);
            sql = "select acc_no from Login where username='"+user+"'";
            rs = statement.executeQuery(sql);
            rs.next();
            String acc = rs.getString(1);
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
            java.util.Date date = new java.util.Date();
            SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
            String instance = formatter.format(date);
            sql = "insert into transaction values('"+instance+"','"+acc+"','"+account+"','"+amount+"')";
            statement.execute(sql);
            alert.setTitle("Success");
            alert.setHeaderText("Successful Transfer Of "+amount+" INR");
            alert.setContentText("Click Ok to proceed");
            alert.showAndWait();
            String file="home.fxml";
            if(Objects.equals(user, "ADMIN"))
                file = "admin_home.fxml";
            FXMLLoader Loader = new FXMLLoader(getClass().getResource(file));
            root = Loader.load();
            Home cont = Loader.getController();
            cont.display(user);
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        connect.close();
    }
    public void username(String user) {
        this.user = user;
    }

    public void reset(ActionEvent e) {
        acc_no.clear();
        amt.clear();
    }
}
