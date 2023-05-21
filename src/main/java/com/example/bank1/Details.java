package com.example.bank1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Details {
    private String user;
    private Stage stage;
    private Parent root;
    private FXMLLoader Loader;

    @FXML
    Label a,b,c,d,e,f,g,h,i;

    public void username(String user) throws Exception {
        this.user = user;
        showDetails();
    }
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
    public void showDetails() throws Exception{
        Statement statement;
        ResultSet rs = null;
        try{
            Connection connect = new DatabaseConnection().getConnection();
            statement = connect.createStatement();
            String sql = "select * from Login where username='"+user+"'";
            rs = statement.executeQuery(sql);
            rs.next();
            String acc_no = rs.getString(1)+"\n";
            String balance = rs.getString(4)+"\n";
            String aadhar = rs.getString(5)+"\n";
            String mobile = rs.getString(6)+"\n";
            String email = rs.getString(7)+"\n";
            String address = rs.getString(8)+"\n";
            String dob = rs.getString(9)+"\n";
            String gender = rs.getString(10)+"\n";
            String acc_type = rs.getString(11)+"\n";

            a.setText("Account No : . "+acc_no);
            b.setText("Available Balance : . "+balance);
            c.setText("Aadhar No : . "+aadhar);
            d.setText("Mobile No : . "+mobile);
            e.setText("Email : "+email);
            f.setText("Address: "+address);
            g.setText("Date Of Birth : "+dob);
            h.setText("Gender : "+gender);
            i.setText("Account Type : "+acc_type);

            connect.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
