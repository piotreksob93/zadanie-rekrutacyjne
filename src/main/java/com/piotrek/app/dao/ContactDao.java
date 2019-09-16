package com.piotrek.app.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ContactDao {

    void saveContact(Connection connection, int id, String contact) throws SQLException;
}
