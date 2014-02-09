package com.globalcontrolsystem.gcs.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.globalcontrolsystem.gcs.Const.ApplicationConst;
import com.globalcontrolsystem.gcs.Network.Configuration;
import com.globalcontrolsystem.gcs.Network.NetworkAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 03.02.14.
 */
public class GPStracker{
    private static Context _context;
    private static GPStracker Instance;

    private GPStracker(){
        LocationManager locationManager = (LocationManager) _context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new GPSLocationListener();
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

    }

    public static GPStracker GetInstance(Context context){
        _context = context;
        if (Instance == null){
            Instance = new GPStracker();
        }
        return Instance;
    }

    /*----------Listener class to get coordinates ------------- */
    private class GPSLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            final NetworkAdapter adapter = new NetworkAdapter();

            final JSONObject jsonParam = new JSONObject();
            try {
                SharedPreferences settings = _context.getSharedPreferences(ApplicationConst.PREFS_NAME, 0);

                jsonParam.put("cid", String.valueOf(settings.getInt(ApplicationConst.PREF_CAMPAIGN_ID, 0)));
                jsonParam.put("deviceID", String.valueOf(settings.getInt(ApplicationConst.PREF_DEVICE_ID, 0)));
                jsonParam.put("lon", String.valueOf(loc.getLatitude()));
                jsonParam.put("lat", String.valueOf(loc.getLongitude()));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String send = adapter.Send(Configuration.MakeSetGeoUrl(), jsonParam);
                        Log.w("SendGPS", send);
                    }
                }).start();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}
