package com.example.maciek.eresviewer;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Maciek on 04.08.2017.
 */

public class Preferences {
    private static SharedPreferences sp;

    public static void saveString(String key, String value, Context context){
        sp=context.getSharedPreferences(context.getString(R.string.preferences_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(String key, Context context){
        sp=context.getSharedPreferences(context.getString(R.string.preferences_name), Context.MODE_PRIVATE);
        return sp.getString(key, "nie ma");
    }

    public static void saveCredentials(String login, String password, Context context){
        sp=context.getSharedPreferences(context.getString(R.string.preferences_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        String encryptedLogin=EncryptionHelper.encryptData(login, context.getString(R.string.login_IV), context);
        String encryptedPassword=EncryptionHelper.encryptData(password, context.getString(R.string.password_IV), context);
        editor.putString("login", encryptedLogin);

        editor.putString("password", encryptedPassword);
        editor.commit();
    }

    public static Boolean isLoggedIn(Context context){
        sp=context.getSharedPreferences(context.getString(R.string.preferences_name), Context.MODE_PRIVATE);
        if(sp.getString("password", null)!=null)
            return true;
        else
            return false;
    }
    public static void removeCredentials(Context context){
        sp=context.getSharedPreferences(context.getString(R.string.preferences_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.remove("login");
        editor.remove("password");
        editor.commit();
    }
    public static String getLogin(Context context){
        sp=context.getSharedPreferences(context.getString(R.string.preferences_name), Context.MODE_PRIVATE);
        String login=sp.getString("login", "brak");
        return EncryptionHelper.decryptData(login, context.getString(R.string.login_IV), context);
    }
    //TODO: Czy te metody mogą być public static
    public static String getPassword(Context context){
        sp=context.getSharedPreferences(context.getString(R.string.preferences_name), Context.MODE_PRIVATE);
        String encryptedPassword=sp.getString("password", "brak");
        return EncryptionHelper.decryptData(encryptedPassword, context.getString(R.string.password_IV), context);
    }
}
