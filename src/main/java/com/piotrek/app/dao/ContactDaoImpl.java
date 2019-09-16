package com.piotrek.app.dao;

import com.piotrek.app.helpers.ContactTypeRecognizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDaoImpl implements ContactDao {

    private ContactTypeRecognizer contactTypeRecognizer = new ContactTypeRecognizer();

    @Override
    public void saveContact(Connection connection, int id, String contact) throws SQLException {
        String query = "INSERT INTO `zadanie`.`contacts` (`ID_CUSTOMER`,`TYPE`, `CONTACT`) VALUES (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, contactTypeRecognizer.recognizeContactType(contact));
        preparedStatement.setString(3, contact);
        preparedStatement.execute();
    }
}
