package com.example.senatorojonla.ubhs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rajah on 3/2/2017.
 */

public class UserPreferences {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context _context;

    @SuppressLint("CommitPrefEdits")
    public UserPreferences(Context _context) {
        this._context = _context;
        sharedPreferences = _context.getSharedPreferences(Constant.USER_PREF, Constant.PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public boolean saveUserProfile(String user_id, String username, String surname, String othernames, String phone, String contact, String email, String sex, String status){
        editor.putString(Constant.USER_ID, user_id);
        editor.putString(Constant.USERNAME, username);
        editor.putString(Constant.SURNAME, surname);
        editor.putString(Constant.OTHERNAMES, othernames);
        editor.putString(Constant.PHONE, phone);
        editor.putString(Constant.CONTACT, contact);
        editor.putString(Constant.EMAIL, email);
        editor.putString(Constant.SEX, sex);
        editor.putString(Constant.STATUS,status);
        return true;
    }


    public String getUsername() {
        return sharedPreferences.getString(Constant.USERNAME, "");
    }


    public static void getString(String USERNAME, Object o) {
    }}
