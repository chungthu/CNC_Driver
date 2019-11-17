package com.example.cnc_driver.common.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chungnt on 17,July,2019
 * Hiworld JSC.
 */
public class JsonUtils {

    /**
     *
     * Check string is json or not.
     *
     * @param test
     * @return
     */
    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * Check string is json array or not.
     *
     * @param test
     * @return
     */
    public static boolean isJSONArray(String test) {
        try {
            new JSONArray(test);
        } catch (JSONException ex1) {
            return false;
        }
        return true;
    }
}
