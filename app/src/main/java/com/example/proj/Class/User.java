package com.example.proj.Class;

public class User {
    public String First_name, Last_name, ID_Card, address, Phone_number, email, uid, password;

    public User() {
    }

    public User(String first_name, String last_name, String ID_Card, String address, String phone_number, String email, String uid, String password) {
        First_name = first_name;
        Last_name = last_name;
        this.ID_Card = ID_Card;
        this.address = address;
        Phone_number = phone_number;
        this.email = email;
        this.uid = uid;
        this.password = password;
    }
}
