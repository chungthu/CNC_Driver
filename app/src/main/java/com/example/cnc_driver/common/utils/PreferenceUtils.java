package com.example.cnc_driver.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * Created by chungnt on 17,July,2019
 * Hiworld JSC.
 */
public class PreferenceUtils {
    /**
     *
     * Save data to preference with key and value.
     *
     * @param context
     * @param key
     * @param data
     */
    public static void savePreference(Context context, String key, Object data) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        if (data instanceof String) {
            editor.putString(key, String.valueOf(data));
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Set) {
            editor.putStringSet(key, (Set<String>) data);
        }
        editor.commit();
    }

    /**
     *
     * Get data on preference with key and value.
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object getPreference(Context context, String key, Object defaultValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (defaultValue instanceof String) {
            return preferences.getString(key, String.valueOf(defaultValue));
        } else if (defaultValue instanceof Integer) {
            return preferences.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return preferences.getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Float) {
            return preferences.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return preferences.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Set) {
            return preferences.getStringSet(key, (Set<String>) defaultValue);
        }

        return null;
    }

    /**
     *
     * Remove data on preference with key.
     *
     * @param context
     * @param key
     */
    public static void removePreference(Context context, String key) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.remove(key);
        editor.commit();
    }
}

