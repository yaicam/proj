package com.example.proj.Class;

public class Revenue_Model {
    Float Value;
    String Date,Detail;

    public Float getValue() {
        return Value;
    }

    public String getDate() {
        return Date;
    }

    public String getDetail() {
        return Detail;
    }

    public Revenue_Model(Float value, String date, String detail) {
        Value = value;
        Date = date;
        Detail = detail;
    }

    public Revenue_Model() {
    }

}
