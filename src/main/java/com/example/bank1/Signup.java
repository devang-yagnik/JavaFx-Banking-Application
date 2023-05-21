package com.example.bank1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

public class Signup {

    @FXML
    Label acc_no;
    String account;
    public void disp(String acc){
        account = acc;
        acc_no.setText(acc);
    }
    @FXML
    TextField uname;
    @FXML
    PasswordField pswd;
    @FXML
    TextField adhar;
    @FXML
    TextField mobile;
    @FXML
    TextField mail;
    @FXML
    DatePicker dob;
    @FXML
    TextArea address;
    @FXML
    ToggleGroup gender,type;

    String getGender(){
        System.out.println(((RadioButton)gender.getSelectedToggle()).getText());
        return ((RadioButton)gender.getSelectedToggle()).getText();
    }
    String getType(){
        return ((RadioButton)type.getSelectedToggle()).getText();
    }
    public void sign(ActionEvent e) throws Exception {
        Connection connect = new DatabaseConnection().getConnection();
        Statement statement = connect.createStatement();
        ResultSet rs = null;
        try {
            String name = uname.getText().trim();
            String password = pswd.getText().trim();
            String aadhar = adhar.getText().trim();
            String mob = mobile.getText().trim();
            String email = mail.getText().trim();
            Date date = Date.valueOf(dob.getValue());
            String adr = address.getText().trim();
            String gender = getGender();
            String type = getType();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if(password.equals("")){
                alert.setTitle("Invalid");
                alert.setHeaderText("Password Cannot be Empty!");
                alert.setContentText("write valid password");
                alert.showAndWait();
                return;
            }
            else if(aadhar.length()!=12){
                alert.setTitle("Invalid");
                alert.setHeaderText("Invalid Aadhar number!");
                alert.setContentText("write 12 digit aadhar number");
                alert.showAndWait();
                return;
            }
            else if(mob.length()!=10){
                alert.setTitle("Invalid");
                alert.setHeaderText("Invalid Mobile number!");
                alert.setContentText("write 10 digit mobile number");
                alert.showAndWait();
                return;
            }
            String sql = "select * from Login where username='" + name + "'";
            rs = statement.executeQuery(sql);
            if(rs.next()){
                alert.setTitle("Username");
                alert.setContentText("Use a different username.");
                alert.setHeaderText("Username Already Exists!");
                alert.showAndWait();
                return;
            }
            alert.setTitle("Confirm");
            alert.setHeaderText("Confirm Initial Deposit Of 1000 INR");
            alert.setContentText("Click Ok to proceed");
            if(alert.showAndWait().get() == ButtonType.CANCEL){
                return;
            }
            sql = "insert into Login(acc_no,username,password,balance,aadhar,mobile,email,address,date,gender,acc_type) values('"+account+"','"+name+"','"+password+"',"+1000+",'"+aadhar+"','"+mob+"','"+email+"','"+adr+"','"+date+"','"+gender+"','"+type+"')";
            int status = statement.executeUpdate(sql);

            if(status==1) {
                alert.setTitle("SignUp Successful");
                alert.setContentText("SignUp Successful");
                alert.setHeaderText("You've SignedUp");
                alert.showAndWait();
                Stage stg = (Stage)((Node)e.getSource()).getScene().getWindow();
                stg.close();
            }
            else{
                alert.setTitle("LogIn Unsuccessful");
                alert.setContentText("LogIn Unsuccessful");
                alert.setHeaderText("Username or Password Incorrect");
                alert.showAndWait();
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }

        connect.close();
    }
}
