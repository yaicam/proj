package com.example.proj.Class;

import java.util.Date;

public class User_Model {
    Float SumValue ;
    Date Date;

    public Float getSumValue() {
        return SumValue;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public User_Model() {
    }

    public User_Model(Float sumValue, java.util.Date date) {
        SumValue = sumValue;
        Date = date;
    }
}
