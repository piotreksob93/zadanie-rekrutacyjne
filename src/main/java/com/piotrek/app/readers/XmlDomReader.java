package com.piotrek.app.readers;

import com.piotrek.app.dao.ContactDaoImpl;
import com.piotrek.app.dao.CustomerDaoImpl;
import com.piotrek.app.helpers.DbConnection;
import com.piotrek.app.helpers.PropertiesReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class XmlDomReader {

    private CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private ContactDaoImpl contactDao = new ContactDaoImpl();
    private PropertiesReader propertiesReader = new PropertiesReader();

    public void readAndSaveFromXML() throws IOException, ParserConfigurationException, SAXException, SQLException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Properties properties = propertiesReader.readProperties();
        DbConnection dbConnection = new DbConnection();

        try (Connection connection = dbConnection.connectToDatabase()) {
            if (connection != null) {
                InputStream inputStream = new FileInputStream(properties.getProperty("xml_file_path"));
                Reader reader = new InputStreamReader(inputStream, "UTF-8");
                InputSource is = new InputSource(reader);
                is.setEncoding("UTF-8");

                Document document = builder.parse(is);
                document.getDocumentElement().normalize();
                NodeList persons = document.getElementsByTagName("person");

                int id;
                for (int i = 0; i < persons.getLength(); i++) {
                    Node personNode = persons.item(i);
                    if (personNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) personNode;
                        try {
                            id = customerDao.saveCustomer(connection, element.getElementsByTagName("name").item(0).getTextContent(),
                                    element.getElementsByTagName("surname").item(0).getTextContent(),
                                    element.getElementsByTagName("age").item(0).getTextContent());
                        } catch (NullPointerException e) {
                            id = customerDao.saveCustomer(connection, element.getElementsByTagName("name").item(0).getTextContent(),
                                    element.getElementsByTagName("surname").item(0).getTextContent(),
                                    null);
                        }

                        NodeList contacts = ((Element) personNode).getElementsByTagName("contacts");
                        Node tempNode = contacts.item(0);
                        if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                            for (int j = 0; j < tempNode.getChildNodes().getLength(); j++) {
                                if (tempNode.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                                    contactDao.saveContact(connection, id, tempNode.getChildNodes().item(j).getTextContent());
                                }
                            }
                        }
                    }
                }
                System.out.println("Pomyślnie odczytanu dane z pliku XML");
            } else {
                System.out.println("Wystąpił błąd podczas łączenia się z bazą danych");
            }
        }
    }
}

