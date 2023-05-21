package com.example.bank1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Account_Holders {
    @FXML
    private TableColumn<user, String> acc_no;

    @FXML
    private TableColumn<user, String> address;

    @FXML
    private TableColumn<user, String> email;

    @FXML
    private TableView<user> holder_table;

    @FXML
    private TableColumn<user, String> mobile;

    @FXML
    private TableColumn<user, String> name;

    private Stage stage;
    private Parent root;
    public String user,account;
    public void username(String user) throws Exception {
        this.user = user;
        setHolder_table();
    }
    public void prev(MouseEvent e) throws Exception{
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("admin_home.fxml"));
        root = Loader.load();
        Home hm = Loader.getController();
        hm.display(user);
        Scene scene = new Scene(root);
        stage = ((Stage)((Node)e.getSource()).getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }
    ObservableList<user> list = FXCollections.observableArrayList();

    public void setHolder_table() throws Exception {
        Connection connect = new DatabaseConnection().getConnection();
        Statement statement = null;
        try {
            statement = connect.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String sql = "select * from Login";
            rs = statement.executeQuery(sql);
            while(rs.next()){
                list.add(new user(
                        rs.getString(1),
                        rs.getString(8),
                        rs.getString(7),
                        rs.getString(6),
                        rs.getString(2)));
                holder_table.setItems(list);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        acc_no.setCellValueFactory(new PropertyValueFactory<user, String>("acc_no"));
        name.setCellValueFactory(new PropertyValueFactory<user, String>("username"));
        mobile.setCellValueFactory(new PropertyValueFactory<user, String>("mobile"));
        email.setCellValueFactory(new PropertyValueFactory<user, String>("email"));
        address.setCellValueFactory(new PropertyValueFactory<user, String>("address"));

        connect.close();
    }
}
