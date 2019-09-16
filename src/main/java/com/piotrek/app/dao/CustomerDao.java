package com.piotrek.app.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDao {

    int saveCustomer(Connection connection, String name, String surname, String age) throws SQLException;
}
