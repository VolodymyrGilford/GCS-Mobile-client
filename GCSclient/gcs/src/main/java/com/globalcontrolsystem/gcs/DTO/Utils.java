package com.globalcontrolsystem.gcs.DTO;


/**
 * Created by Waserdx on 09.02.14.
 */
public class Utils {
    public static String ClearJson(String json){
        String temp =  json.substring(6, json.length() - 2).replace("\"", "'").replace("\\", "");
        return temp;
    }
}
