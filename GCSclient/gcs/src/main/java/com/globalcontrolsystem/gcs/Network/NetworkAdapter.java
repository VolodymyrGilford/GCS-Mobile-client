package com.globalcontrolsystem.gcs.Network;

import android.support.v7.appcompat.R;
import android.util.Log;

import com.globalcontrolsystem.gcs.Const.NetworkConst;

import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.*;
import org.apache.http.NameValuePair;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.globalcontrolsystem.gcs.DTO.Utils;
import com.google.gson.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Class for work with network
 */
public class NetworkAdapter {

    public NetworkAdapter(){

     }

    /**
    * @param url - request url
    * @param data - post data
    * @return - server response
    */
    public String Send(String url, JSONObject data){
        return  PostRequest(url, data.toString());
    }

    public String Send(String url, JsonObject data){
        return PostRequest(url, data.toString());
    }

    private String PostRequest(String url, String data){
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new StringEntity(data));

            httppost.setHeader("Content-type", "application/json");

            try{
                HttpResponse response = httpclient.execute(httppost);
                String json = Utils.ClearJson(EntityUtils.toString(response.getEntity()));
                return json;
            }
            catch (Exception e) {
                Log.w("NetworkAdapter", e.getMessage());
            }

        } catch (IOException e) {
            Log.w("NetworkAdapter", e.getMessage());
        }

        return NetworkConst.PostRequestError;
    }

}
