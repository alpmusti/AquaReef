package com.cantekin.aquareef.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;


/**
 * preferences e erişmek
 * genel ayarları
 * kaydetmek ve okumak için
 * Created by Cantekin on 11.01.2016.
 * singleton pattern
 */

public class MyPreference {
    private static String TAG = "Preference";
    private Context context;
    public static MyPreference preference;
    public static String FAVORITES = "favorites";
    public static String GRUPS = "Grups";
    public static SharedPreferences data;

    private MyPreference(Context context) {
        this.context = context;
    }

    public static MyPreference getPreference(Context context) {
        if (preference == null)
            preference = new MyPreference(context);
        if (data == null)
            data = PreferenceManager.getDefaultSharedPreferences(context);
        return preference;
    }

    //region Get
    public <T> T getData(String key, Class<T> clazzType) {
        String dataString = data.getString(key, null);
        if (dataString == null)
            return null;
        return jsonHelper.stringToObject(dataString, clazzType);
    }

    public String getData(String key) {
        return data.getString(key, null);
    }

    public void setData(String key, Object value) {
        SharedPreferences.Editor editor = data.edit();
        if (value != null && key != null)
            editor.putString(key, jsonHelper.objectToJson(value));
        else
            throw new NullPointerException("paramtreler null olamaz");
        editor.commit();
    }

    //endregion
    //region Set
    public void setData(@NonNull String name, @NonNull String value) throws NullPointerException {
        SharedPreferences.Editor editor = data.edit();
        if (value != null && name != null) editor.putString(name, value);
        else
            throw new NullPointerException("paramtreler null olamaz");
        editor.commit();
    }

    //endregion
    //region delete
    public void clearPreferences() {
        data.edit().clear().commit();
    }

    public void deleteValue(String key) {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = data.edit();
        if (key != null) editor.remove(key);
        editor.commit();
    }


}
