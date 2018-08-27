package com.studios.uio443.cluck.data.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import static com.studios.uio443.cluck.data.util.Consts.SHARED_VALUES;

public class SharedPreferencesUtil {

    private static SharedPreferences sharedPrefPrivate = null;

    public static void initSharedPreferences(Context context) {
        if (sharedPrefPrivate == null)
            sharedPrefPrivate = context.getSharedPreferences(
                    SHARED_VALUES, Context.MODE_PRIVATE);
    }

    public static void saveProperty(String propertyName, int value) {
        SharedPreferences.Editor editor = sharedPrefPrivate.edit();
        editor.putInt(propertyName, value);
        editor.apply();
    }

    public static int getProperty(String propertyName, int defValue) {
        return sharedPrefPrivate.getInt(propertyName, defValue);
    }

    public static void saveProperty(String propertyName, long value) {
        SharedPreferences.Editor editor = sharedPrefPrivate.edit();
        editor.putLong(propertyName, value);
        editor.apply();
    }

    public static long getProperty(String propertyName, long defValue) {
        return sharedPrefPrivate.getLong(propertyName, defValue);
    }

    public static void saveProperty(String propertyName, boolean value) {
        SharedPreferences.Editor editor = sharedPrefPrivate.edit();
        editor.putBoolean(propertyName, value);
        editor.apply();
    }

    public static boolean getProperty(String propertyName, boolean defValue) {
        return sharedPrefPrivate.getBoolean(propertyName, defValue);
    }

    private static void saveProperty(String propertyName, String value) {
        SharedPreferences.Editor editor = sharedPrefPrivate.edit();
        editor.putString(propertyName, value);
        editor.apply();
    }

    private static String getProperty(String propertyName, String defValue) {
        return sharedPrefPrivate.getString(propertyName, defValue);
    }

    public static void savePropertyWithEncrypt(String propertyName, String value) {
        saveProperty(Base64.encodeToString(propertyName.getBytes(), Base64.DEFAULT),
                Base64.encodeToString(value.getBytes(), Base64.DEFAULT));
    }

    public static String getPropertyWithDecrypt(String propertyName, String defValue) {
        return new String(Base64.decode(getProperty(Base64.encodeToString(propertyName.getBytes(), Base64.DEFAULT),
                Base64.encodeToString(defValue.getBytes(), Base64.DEFAULT)), Base64.DEFAULT));
    }

}
