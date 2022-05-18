package com.example.mymealdabba;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;


public class SessionManager {


    public  static  final String KEY_CITY_ID="city_id";

    public static final String KEY_ID = "id";

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

    public void createSUserDetals(String name,String email,String phone) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE, phone);
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
}



