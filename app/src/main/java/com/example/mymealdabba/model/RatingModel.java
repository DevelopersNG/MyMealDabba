package com.example.mymealdabba.model;

public class RatingModel {
    public String ID;
    public String Rating;
    public String ReviewerName;
    public String ReviewerNo;
    public String ReviewerEmail;
    public String AddedOn;
    public String MemberID;



    public RatingModel(String ID, String rating, String reviewerName, String reviewerNo, String reviewerEmail, String addedOn, String memberID) {
        this.ID = ID;
        Rating = rating;
        ReviewerName = reviewerName;
        ReviewerNo = reviewerNo;
        ReviewerEmail = reviewerEmail;
        AddedOn = addedOn;
        MemberID = memberID;

    }


}
