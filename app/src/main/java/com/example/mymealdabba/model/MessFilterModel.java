package com.example.mymealdabba.model;

public class MessFilterModel {
    public  String apikey;

    public String CityID;
    public  String UserID;
    public String MessType;
    public  String Service;
    public String SortBy;
    public  String Category;
    public  String Location;
    public  String CuisineType;

    public MessFilterModel(String apikey, String cityID, String userID, String messType, String service, String sortBy, String category, String location, String cuisineType) {
        this.apikey = apikey;
        CityID = cityID;
        UserID = userID;
        MessType = messType;
        Service = service;
        SortBy = sortBy;
        Category = category;
        Location = location;
        CuisineType = cuisineType;
    }

}
