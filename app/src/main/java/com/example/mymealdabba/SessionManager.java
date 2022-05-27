package com.example.mymealdabba;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.mymealdabba.model.RatingModel;
import com.example.mymealdabba.model.ReviewsModel;
import com.example.mymealdabba.model.SessionModel;

import java.util.HashMap;


public class SessionManager {


    public  static  final String KEY_CITY_ID="city_id";
// mess rating......
    public static final  String KEY_ID1= "ID";
    public static final String  KEY_RATING= "Rating";
    public  static final String  KEY_REVIEWERNAME="ReviewerName";
    public static final String KEY_REVIEWERNO ="ReviewerNo";
    public static final String KEY_ReMAIL="ReviewerEmail";
    public static final String  KEY_DATE="AddedOn";
    public static final String  KEY_MEMBER_ID="MemberID";

//bookmark detail
    public static final  String KEY_IDB= "BID";
    public static final String  KEY_RATINGB= "BRating";
    public  static final String  KEY_REVIEWERNAMEB="BReviewerName";
    public static final String KEY_REVIEWERNOB ="BReviewerNo";
    public static final String KEY_ReMAILB="BReviewerEmail";
    public static final String  KEY_DATEB="BAddedOn";
    public static final String  KEY_MEMBER_IDB="BMemberID";



    public static final String KEY_AVGREVIEW="avgreview";
    public static final String KEY_AVGREVIEWB="Bavgreview";

    public static final String KEY_ID = "id";
    public static final String KEY_RESULT = "result";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";

    private static final String PREF_NAME = "userData";
    private static final String IS_LOGIN = "isLogin";
    private static SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    @SuppressLint("CommitPrefEdits")

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createSessionLogin(String id) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getId() {
        return pref.getString(KEY_ID, null);
    }


    public void setCityId(String city_id) {
        editor.putString(KEY_CITY_ID, city_id);
        editor.commit();
    }

    public String getCityId() {
        return pref.getString(KEY_CITY_ID, "");
    }


    public void clearSession() {
        editor.clear();
        editor.commit();
    }
    public void logoutUser() {
        clearSession();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);

    }

    public void createSUserDetails(SessionModel response) {
        editor.putString(KEY_NAME, response.Name);
        editor.putString(KEY_EMAIL, response.Email);
        editor.putString(KEY_PHONE, response.contact_no);
        editor.commit();
    }

    public String getName() {
        return pref.getString(KEY_NAME, " ");
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public String getPhone() {
        return pref.getString(KEY_PHONE, "");
    }

    public void createReviewerDetails(RatingModel response) {
        editor.putString(KEY_ID1,response.ID);
        editor.putString(KEY_REVIEWERNAME, response.ReviewerName);
        editor.putString(KEY_RATING, response.Rating);
        editor.putString(KEY_REVIEWERNO, response.ReviewerNo);
        editor.putString(KEY_DATE, response.AddedOn);
        editor.commit();
    }

    public void setMemberId(String memberId) {
        editor.putString(KEY_MEMBER_ID, memberId);
        editor.commit();
    }

    public String getMemberId() {
        return pref.getString(KEY_MEMBER_ID, "");
    }



    public void setAvgReview(String avgReview) {
        editor.putString(KEY_AVGREVIEW, avgReview);
        editor.commit();
    }

    public String getAvgReview() {
        return pref.getString(KEY_AVGREVIEW, "");
    }

    public String getId1() {
        return pref.getString(KEY_ID1, "");
    }


    public void BookmarkReviewerDetails(RatingModel response) {
        editor.putString(KEY_IDB,response.ID);
        editor.putString(KEY_REVIEWERNAMEB, response.ReviewerName);
        editor.putString(KEY_RATINGB, response.Rating);
        editor.putString(KEY_REVIEWERNOB, response.ReviewerNo);
        editor.putString(KEY_DATEB, response.AddedOn);
        editor.commit();
    }




    public void setMemberIdB(String BmemberId) {
        editor.putString(KEY_MEMBER_IDB, BmemberId);
        editor.commit();
    }

    public String getMemberIdB() {
        return pref.getString(KEY_MEMBER_IDB, "");
    }



    public void setAvgReviewB(String BavgReview) {
        editor.putString(KEY_AVGREVIEWB, BavgReview);
        editor.commit();
    }

    public String getAvgReviewB() {
        return pref.getString(KEY_AVGREVIEWB, "");
    }


}



