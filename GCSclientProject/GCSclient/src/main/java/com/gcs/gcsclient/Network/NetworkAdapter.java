package com.gcs.gcsclient.Network;

import android.support.v7.appcompat.R;
import android.util.Log;

import com.gcs.gcsclient.Const.NetworkConst;
import com.google.gson.Gson;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
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
    public String Send(String url, List<NameValuePair> data){
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));

            try{
                HttpResponse response = httpclient.execute(httppost);
                return EntityUtils.toString(response.getEntity());
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
