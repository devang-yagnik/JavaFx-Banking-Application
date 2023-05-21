package com.example.bank1;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public Connection getConnection() throws Exception{
        String databaseName = "mysql";
        String databaseUser = "root";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost:3306/"+databaseName;

        return  DriverManager.getConnection(url,databaseUser,databasePassword);

    }
}
