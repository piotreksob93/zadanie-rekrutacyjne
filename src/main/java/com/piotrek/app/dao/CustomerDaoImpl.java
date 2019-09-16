package com.piotrek.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public int saveCustomer(Connection connection, String name, String surname, String age) throws SQLException {

        String query = "INSERT INTO `zadanie`.`customers` (`NAME`, `SURNAME`, `AGE`) VALUES (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, age);
        preparedStatement.execute();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }

        return 0;
    }
}
