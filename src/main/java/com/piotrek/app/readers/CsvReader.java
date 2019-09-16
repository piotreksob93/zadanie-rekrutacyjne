package com.piotrek.app.readers;

import com.piotrek.app.dao.ContactDaoImpl;
import com.piotrek.app.dao.CustomerDaoImpl;
import com.piotrek.app.helpers.DbConnection;
import com.piotrek.app.helpers.PropertiesReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class CsvReader {

    private CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private ContactDaoImpl contactDao = new ContactDaoImpl();
    private PropertiesReader propertiesReader = new PropertiesReader();

    public void readAndSaveFromCsv() throws IOException, SQLException {
        String row;
        Properties properties = propertiesReader.readProperties();
        DbConnection dbConnection = new DbConnection();

        try (Connection connection = dbConnection.connectToDatabase()) {
            if (connection != null) {
                BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(properties.getProperty("csv_file_path")), "UTF-8"));

                while ((row = csvReader.readLine()) != null) {
                    try {
                        String[] data = row.split(",");

                        int id = customerDao.saveCustomer(connection, data[0], data[1], data[2].equals("") ? null : data[2]);

                        for (int i = 4; i < data.length; i++) {
                            if(!data[i].equals("")){
                                contactDao.saveContact(connection, id, data[i]);
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                System.out.println("Pomyślnie odczytanu dane z pliku CSV");
                csvReader.close();
            } else {
                System.out.println("Wystąpił błąd podczas łączenia się z bazą danych");
            }
        }
    }
}
