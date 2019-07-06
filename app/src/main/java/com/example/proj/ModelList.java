package com.example.proj;

public class ModelList {
    public ModelList() {
    }

    String First_name, Last_name, ID_Card, address, Phone_number, email,uid;

    public ModelList(String first_name, String last_name, String ID_Card, String address, String phone_number, String email, String uid) {
        First_name = first_name;
        Last_name = last_name;
        this.ID_Card = ID_Card;
        this.address = address;
        Phone_number = phone_number;
        this.email = email;
        this.uid = uid;
    }

    public String getFirst_name() {
        return First_name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public String getID_Card() {
        return ID_Card;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}
