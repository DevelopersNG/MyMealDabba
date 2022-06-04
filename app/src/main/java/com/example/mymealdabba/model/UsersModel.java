package com.example.mymealdabba.model;

public class UsersModel {
    public String id;
    public String name;
    public String contact;
    public String email;
    public String result;
    public String AppVersion;
    public String OSVersion;

    public UsersModel(String id, String name, String contact, String email, String result, String appVersion, String OSVersion) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.result = result;
        AppVersion = appVersion;
        this.OSVersion = OSVersion;
    }
}
