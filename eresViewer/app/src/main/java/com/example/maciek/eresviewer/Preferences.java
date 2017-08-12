package com.example.maciek.eresviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.security.KeyStore;

/**
 * Created by Maciek on 04.08.2017.
 */

public class Preferences {
    private static SharedPreferences sp;
    private static final String myPREFERENCES="MyPrefs";


    public static void saveString(Context context, String key, String value){
        sp=context.getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE);
        sp.edit().putString(key,value);
        sp.edit().commit();
    }

    public static String getString(Context context, String key){
        sp=context.getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }

    public static void saveCredentials(Context context,String login, String password){
        sp=context.getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString("login", login);
        editor.putString("password", password);
        editor.commit();
    }

    public static Boolean isLoggedIn(Context context){
        sp=context.getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE);
        if(sp.getString("password", null)!=null)
            return true;
        else
            return false;
    }
    public static void removeCredentials(Context context){
        sp=context.getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.remove("login");
        editor.remove("password");
        editor.commit();
    }
    public static String getLogin(Context context){
        sp=context.getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE);
        return sp.getString("login", "brak");
    }
    public static String getPassword(Context context){
        sp=context.getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE);
        return sp.getString("password", "brak");
    }
}
