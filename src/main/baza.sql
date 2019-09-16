DROP DATABASE IF EXISTS ZADANIE;
CREATE DATABASE ZADANIE;
USE ZADANIE;
CREATE TABLE CUSTOMERS (
    ID INT NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(30) NOT NULL,
    SURNAME VARCHAR(50) NOT NULL,
    AGE INT,
    PRIMARY KEY (ID)
);
CREATE TABLE CONTACTS (
	ID int NOT NULL AUTO_INCREMENT,
	ID_CUSTOMER INT,
    TYPE INT NOT NULL,
    CONTACT VARCHAR(100),
    PRIMARY KEY (ID),
    FOREIGN KEY (ID_CUSTOMER) REFERENCES CUSTOMERS(ID)
);