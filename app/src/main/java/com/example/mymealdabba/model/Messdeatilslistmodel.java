package com.example.mymealdabba.model;

import android.widget.ImageView;

public class Messdeatilslistmodel {
    int img;
    public boolean getType;
    String MessName,MessAddress,MessMonthlyRate,TotalViews,MessType,MessCategory,MessService,MessExperience;

    public Messdeatilslistmodel(int img, String messName, String messAddress, String messMonthlyRate, String totalViews, String messType, String messCategory, String messService, String messExperience) {
        this.img = img;

        MessName = messName;
        MessAddress = messAddress;
        MessMonthlyRate = messMonthlyRate;
        TotalViews = totalViews;
        MessType = messType;
        MessCategory = messCategory;
        MessService = messService;
        MessExperience = messExperience;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isGetType() {
        return getType;
    }

    public void setGetType(boolean getType) {
        this.getType = getType;
    }

    public String getMessName() {
        return MessName;
    }

    public void setMessName(String messName) {
        MessName = messName;
    }

    public String getMessAddress() {
        return MessAddress;
    }

    public void setMessAddress(String messAddress) {
        MessAddress = messAddress;
    }

    public String getMessMonthlyRate() {
        return MessMonthlyRate;
    }

    public void setMessMonthlyRate(String messMonthlyRate) {
        MessMonthlyRate = messMonthlyRate;
    }

    public String getTotalViews() {
        return TotalViews;
    }

    public void setTotalViews(String totalViews) {
        TotalViews = totalViews;
    }

    public String getMessType() {
        return MessType;
    }

    public void setMessType(String messType) {
        MessType = messType;
    }

    public String getMessCategory() {
        return MessCategory;
    }

    public void setMessCategory(String messCategory) {
        MessCategory = messCategory;
    }

    public String getMessService() {
        return MessService;
    }

    public void setMessService(String messService) {
        MessService = messService;
    }

    public String getMessExperience() {
        return MessExperience;
    }

    public void setMessExperience(String messExperience) {
        MessExperience = messExperience;
    }
}
