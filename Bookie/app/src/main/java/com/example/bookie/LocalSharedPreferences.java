package com.example.bookie;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalSharedPreferences {

    private static final String strSharedPrefName = "AppPrefrence";

    public static void saveIsLogin(Context context, boolean isLogin) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isLogin", isLogin);
        editor.commit();
    }

    public static boolean getIsLogin(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        return pref.getBoolean("isLogin", false);
    }

    public static String getname(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        String wish = pref.getString("name", "");
        return wish;
    }

    public static void savename(Context context, String chr) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", chr);
        editor.commit();

    }


    public static String getemail(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        String wish = pref.getString("email", "");
        return wish;
    }

    public static void saveemail(Context context, String chr) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email", chr);
        editor.commit();

    }


    public static String getUserid(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        String wish = pref.getString("userid", "");
        return wish;
    }

    public static void saveUserid(Context context, String chr) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userid", chr);
        editor.commit();

    }

}
