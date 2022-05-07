package com.example.mymealdabba.model;

public class SessionModel {
    public String id;
    public String name;
    public String contact;
    public  String email;
    public String api_key;


    public SessionModel(String id, String name, String contact, String email, String api_key) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.api_key = api_key;
    }



}
