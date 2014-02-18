package com.globalcontrolsystem.gcs.Core;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.globalcontrolsystem.gcs.Const.ApplicationConst;
import com.globalcontrolsystem.gcs.DTO.ResponseAddDevice;
import com.globalcontrolsystem.gcs.DTO.ResponseAuthenticate;
import com.globalcontrolsystem.gcs.DTO.Utils;
import com.globalcontrolsystem.gcs.Network.Configuration;
import com.globalcontrolsystem.gcs.Network.NetworkAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class AccessController{

    private static AccessController Instanse = null;

    private String access_token = "";
    public boolean isAuthenticated = false;
    private Context _context = null;

    private AccessController(Context context){
        _context = context;

        SharedPreferences settings = _context.getSharedPreferences(ApplicationConst.PREFS_NAME, 0);
        access_token = settings.getString(ApplicationConst.PREF_ACCESS_TOKEN, "");
        isAuthenticated = access_token == "" ? false : true;
    }

    public static AccessController GetInstanse(Context context){
        if (Instanse == null){
            Instanse = new AccessController(context);
        }

        return Instanse;
    }

    public String GetToken(){
        if (access_token == "")
            Authenticate();

        return access_token;
    }

    public void Authenticate(){
        JsonObject data = new JsonObject();
        data.addProperty("imei", GetIMEI());

        String json = new NetworkAdapter().Send(Configuration.MakeAuthenticateUrl(), data);
        json = Utils.ClearJson(json);

        ResponseAuthenticate response = new Gson().fromJson(json, ResponseAuthenticate.class);

        if (response.error == null){
            isAuthenticated = true;
            access_token = response.access_token;

            SharedPreferences settings = _context.getSharedPreferences(ApplicationConst.PREFS_NAME, 0);
            settings.edit()
                    .putString(ApplicationConst.PREF_ACCESS_TOKEN, access_token)
                    .commit();
        }
    }

    public String Identificate(String webkey){
        JsonObject data = new JsonObject();
        data.addProperty("webKey", webkey);
        data.addProperty("imei", GetIMEI());
        String json = new NetworkAdapter().Send(Configuration.MakeAddDeviceUrl(), data);

        json = Utils.ClearJson(json);

        ResponseAddDevice response = new Gson().fromJson(json, ResponseAddDevice.class);

        SharedPreferences settings = _context.getSharedPreferences(ApplicationConst.PREFS_NAME, 0);
        settings.edit()
                .putBoolean(ApplicationConst.PREF_IS_REGISTRED, true)
                .putInt(ApplicationConst.PREF_DEVICE_ID, response.deviceID)
                .putInt(ApplicationConst.PREF_CAMPAIGN_ID, response.cid)
                .putString(ApplicationConst.PREF_DEVICE_KEY, response.deviceKey)
                .putString(ApplicationConst.PREF_ACCESS_TOKEN, response.token)
                .commit();

        return response.deviceKey;
    }

    private String GetIMEI(){
        TelephonyManager telephonyManager = (TelephonyManager) _context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

}
