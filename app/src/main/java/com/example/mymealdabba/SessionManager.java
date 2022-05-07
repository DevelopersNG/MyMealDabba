package com.example.mymealdabba;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;


public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_ID = "id";


    @SuppressLint("CommitPrefEdits")

    public SessionManager(Context _context) {
        context = _context;
        sharedPreferences = context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createSessionLogin(String id) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.commit();
    }

    public HashMap<String, String> getDetailsFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_ID, sharedPreferences.getString(KEY_ID, null));
        return userData;
    }

    public boolean isLoggedIn() {
        return  sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public String get(String key) {
        return  sharedPreferences.getString(key, "");
    }

    private void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }

    public void logout() {
        logoutUserFromSession();
        context.startActivity(new Intent(context, LoginActivity.class));
    }

}



