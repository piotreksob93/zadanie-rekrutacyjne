package com.piotrek.app.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    public Properties readProperties() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/app.properties");
        Properties properties = new Properties();
        properties.load(fis);
        return properties;
    }
}