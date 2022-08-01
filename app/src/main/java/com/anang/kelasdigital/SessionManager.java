package com.anang.kelasdigital;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    public static final String MYPREFERENCES = "MyPrefs" ;
    public static final String TOKEN = "TokenKey";
    public static final String UUID = "uuidKey";
    private SharedPreferences sharedpreferences;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);;
    }

    public void store(String key, String val){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public  void clear(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    public String get(String key, String valDefault){
        return sharedpreferences.getString(key, valDefault);
    }
}
