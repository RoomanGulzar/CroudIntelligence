package com.google.croudintelligence.HelpingClass;

import android.content.SharedPreferences;

import com.google.croudintelligence.Models.User;
import com.google.gson.Gson;

public class UserDetails {

    public void setUser(SharedPreferences.Editor editor, User user) {
        editor.putString("user", new Gson().toJson(user));
        editor.apply();
    }

    public User getUser(SharedPreferences sharedPreferences) {
        String val = sharedPreferences.getString("user", null);
        if (val == null) {
            return null;
        } else {
            return new Gson().fromJson(val, User.class);
        }
    }
}
