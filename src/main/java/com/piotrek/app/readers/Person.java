package com.piotrek.app.readers;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private String surname;
    private String age;
    private String city;
    private List<String> contacts;

    Person() {
        contacts = new ArrayList<>();
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getSurname() {
        return surname;
    }

    void setSurname(String surname) {
        this.surname = surname;
    }

    String getAge() {
        return age;
    }

    void setAge(String age) {
        this.age = age;
    }

    String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city;
    }

    List<String> getContacts() {
        return contacts;
    }

    void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
