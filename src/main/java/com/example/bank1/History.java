package com.example.bank1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class History {
    private Parent root;
    private Stage stage;
    private FXMLLoader Loader;
    private String account;
    public String user;

    @FXML
    private TableView<user> history_table;
    @FXML
    private TableColumn<user, Double> amount;

    @FXML
    private TableColumn<user, String> from;

    @FXML
    private TableColumn<user, String> time;

    @FXML
    private TableColumn<user, String> to;

    public void username(String user) throws Exception {
        this.user=user;
        setHistory_table();
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

    ObservableList<user> list = FXCollections.observableArrayList();

    public void setHistory_table() throws Exception {
        Connection connect = new DatabaseConnection().getConnection();
        Statement statement = null;
        try {
            statement = connect.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        try {
            String sql = "select acc_no from Login where username='"+user+"'";
            rs = statement.executeQuery(sql);
            rs.next();
            account = rs.getString(1);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            sql = "select * from transaction where sender='"+account+"' or reciever='"+account+"'";
            rs = statement.executeQuery(sql);
            while(rs.next()){
                list.add(new user(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4)));
                history_table.setItems(list);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        from.setCellValueFactory(new PropertyValueFactory<user, String>("from"));
        to.setCellValueFactory(new PropertyValueFactory<user, String>("to"));
        time.setCellValueFactory(new PropertyValueFactory<user, String>("time"));
        amount.setCellValueFactory(new PropertyValueFactory<user, Double>("amount"));

        connect.close();
    }
}
