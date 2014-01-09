package com.gcs.gcsclient.Network;

import android.support.v7.appcompat.R;
import android.util.Log;
import com.google.gson.Gson;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.*;

/**
 * Class for work with network
 */
public class NetworkAdapter {

    public NetworkAdapter(){

    }

    public String SendPost(String Url, List<NameValuePair> data) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Url);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(data));

            HttpResponse response = httpclient.execute(httppost);
            Gson g = new Gson();
            Log.w("NetworkAdapter", g.toJson(response));

        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return "";
    }



}
