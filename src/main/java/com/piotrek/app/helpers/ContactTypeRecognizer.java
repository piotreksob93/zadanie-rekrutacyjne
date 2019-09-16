package com.piotrek.app.helpers;

public class ContactTypeRecognizer {

    public int recognizeContactType(String contact) {
        if (isEmail(contact)) return 1;
        else if (isPhoneNumber(contact)) return 2;
        else if (isJabber(contact)) return 3;
        else return 0;
    }

    private static boolean isEmail(String email) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private static boolean isPhoneNumber(String phoneNumber) {
        String regex = "^\\d{3}[ ]?\\d{3}[ ]?\\d{3}";
        return phoneNumber.matches(regex);
    }

    private static boolean isJabber(String jabber) {
        return jabber.equals("jbr");
    }
}
