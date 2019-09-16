package com.piotrek.app;

import com.piotrek.app.readers.CsvReader;
import com.piotrek.app.readers.XmlDomReader;
import com.piotrek.app.readers.XmlSaxReader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private static CsvReader csvReader = new CsvReader();
    private static XmlDomReader xmlDomReader = new XmlDomReader();
    private static XmlSaxReader xmlSaxReader = new XmlSaxReader();

    public static void main(String[] args) throws IOException, SQLException, ParserConfigurationException, SAXException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj typ pliku jaki chcesz przetworzyć:");
        System.out.println("1: plik csv");
        System.out.println("2: plik xml");
        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    csvReader.readAndSaveFromCsv();
                    break;
                case 2:
                    System.out.println("Wybierze metodę parsowania pliku");
                    System.out.println("1: parsowanie DOM");
                    System.out.println("2: parsowanie SAX");
                    int method = scanner.nextInt();
                    if (method == 1) {
                        xmlDomReader.readAndSaveFromXML();
                    } else if (method == 2) {
                        xmlSaxReader.readAndSaveFromXML();
                    } else System.out.println("Wybór jest niepoprawny!");
                    break;
                default:
                    System.out.println("Nie wybrano odpowiedniego pliku do przetworzenia");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Wybór pliku do przetworzenia jest niepoprawny!");
        }
    }
}
