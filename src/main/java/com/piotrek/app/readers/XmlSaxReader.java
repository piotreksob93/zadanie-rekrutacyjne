package com.piotrek.app.readers;

import com.piotrek.app.dao.ContactDaoImpl;
import com.piotrek.app.dao.CustomerDaoImpl;
import com.piotrek.app.helpers.DbConnection;
import com.piotrek.app.helpers.PropertiesReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class XmlSaxReader extends DefaultHandler {

    private ContactDaoImpl contactDao = new ContactDaoImpl();
    private CustomerDaoImpl customerDao = new CustomerDaoImpl();

    private DbConnection dbConnection = new DbConnection();

    private List<Person> personList;
    private String tmpValue;
    private Person personTmp;

    public void readAndSaveFromXML() throws SQLException, IOException {
        personList = new ArrayList<>();
        parseDocument();
        saveDataToDb();
    }

    private void parseDocument() throws IOException {

        PropertiesReader propertiesReader = new PropertiesReader();
        Properties properties = propertiesReader.readProperties();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();

            File file = new File(properties.getProperty("xml_file_path"));
            InputStream inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream, "UTF-8");

            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");

            parser.parse(is, this);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Error occurred");
        }
    }

    private void saveDataToDb() throws SQLException {
        try (Connection connection = dbConnection.connectToDatabase()) {
            if (connection != null) {
                int id;

                for (Person tmpPerson : personList) {
                    try {
                        id = customerDao.saveCustomer(connection, tmpPerson.getName(), tmpPerson.getSurname(), tmpPerson.getAge());
                    } catch (NullPointerException e) {
                        id = customerDao.saveCustomer(connection, tmpPerson.getName(), tmpPerson.getSurname(), "");
                    }
                    for (String tmpContact : tmpPerson.getContacts()) {
                        contactDao.saveContact(connection, id, tmpContact);
                    }
                }
                System.out.println("Pomyślnie odczytanu dane z pliku XML");

            } else {
                System.out.println("Wystąpił błąd podczas łączenia się z bazą danych");
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("PERSON")) {
            personTmp = new Person();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("PERSON")) {
            personList.add(personTmp);
        }
        if (qName.equalsIgnoreCase("name")) {
            personTmp.setName(tmpValue);
        }
        if (qName.equalsIgnoreCase("surname")) {
            personTmp.setSurname(tmpValue);
        }
        if (qName.equalsIgnoreCase("age")) {
            personTmp.setAge(tmpValue);
        }
        if (qName.equalsIgnoreCase("city")) {
            personTmp.setCity(tmpValue);
        }
        if (qName.equalsIgnoreCase("phone") ||
                qName.equalsIgnoreCase("email") ||
                qName.equalsIgnoreCase("jabber") ||
                qName.equalsIgnoreCase("icq")
        ) {
            personTmp.getContacts().add(tmpValue);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        tmpValue = new String(ch, start, length);
    }

}
