package com.example.mymealdabba.model;

public class CuisineModel {
    public  String apikey;
    public  String CityID;
    public  String UserID;
    public  String CuisineType;

    public CuisineModel(String apikey, String cityID, String userID, String cuisineType) {
        this.apikey = apikey;
        CityID = cityID;
        UserID = userID;
        CuisineType = cuisineType;
    }


}
