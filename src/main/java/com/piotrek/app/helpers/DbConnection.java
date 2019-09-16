package com.piotrek.app.helpers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    public Connection connectToDatabase() {
        PropertiesReader propertiesReader = new PropertiesReader();
        try {
            Properties properties = propertiesReader.readProperties();
            Class.forName(properties.getProperty("driver"));
            return DriverManager.getConnection(
                    properties.getProperty("db_url"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
        }
        return null;
    }
}


