package com.example.mymealdabba.model;

public class CityModel {


    public String ID;
    public String city;

    public CityModel(String ID, String city) {
        this.ID = ID;
        this.city = city;
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



}
