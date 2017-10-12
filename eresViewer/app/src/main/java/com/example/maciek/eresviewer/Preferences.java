package com.example.maciek.eresviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Maciek on 04.08.2017.
 */

public class Preferences {
    private static SharedPreferences sp;

    public static void saveString(Context context, String key, String value){
        sp= PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(key,value).commit();
        sp.edit().apply();
    }

    public static String getString(Context context, String key){
        sp=PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, null);
    }

    public static void saveCredentials(Context context,String login, String password){
        sp=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString("login", login);
        editor.putString("password", password);
        editor.apply();
    }

    public static Boolean isLoggedIn(Context context){
        sp=PreferenceManager.getDefaultSharedPreferences(context);
        if(sp.getString("password", null)!=null)
            return true;
        else
            return false;
    }
    public static void removeCredentials(Context context){
        sp=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= sp.edit();
        editor.remove("login");
        editor.remove("password");
        editor.apply();
    }
    public static String getLogin(Context context){
        sp=PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("login", "brak");
    }
    public static String getPassword(Context context){
        sp=PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("password", "brak");
    }
}
