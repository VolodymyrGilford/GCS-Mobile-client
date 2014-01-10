package com.example.shadowspy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class StrartActivity extends Activity {
	public static String IMEI;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.w("qqq","1");
		//перевірка чи не зареєстрований пристрій
		SharedPreferences preference = getSharedPreferences(Codes.file_pref, MODE_PRIVATE);
		String saved_login = preference.getString(Codes.saved_login, "");
		String saved_pass = preference.getString(Codes.saved_pass, "");
		
		//отримуємо  imei
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		IMEI = telephonyManager.getDeviceId();	
		Editor editor = preference.edit();
		editor.putString(Codes.saved_IMEI, IMEI);
		editor.commit();	
		Log.w("qqq","2");
		int networkState = getNetworkState();
		switch(networkState){
		case 0:{
			//TODO
			NotifyUtils notify = new NotifyUtils(getApplicationContext(), SingIn.class);
			notify.ShowNotification(Codes.CONNECTION_NOTIFY,
									"Немає з'єднання з сервером", 
									"Увімкніть WiFi або мобільну передачу даних", 
									R.drawable.no_connection_small,
									false);		
			break;
		}
		case 1:{
			editor.putInt(Codes.saved_time_to_refresh, 30*1000);
			editor.commit();
			break;
		}
		case 2:{
			editor.putInt(Codes.saved_time_to_refresh, 5*1000);
			editor.commit();	
			break;
		}
		
		default: break;
		}
		
		Intent SingInActivity = new Intent(getApplicationContext(),SingIn.class);
		if(!saved_login.equals("") && !saved_pass.equals("") && networkState>0){
			SingInActivity.putExtra("Saved", true);
			SingInActivity.putExtra("login", saved_login);
			SingInActivity.putExtra("pass", saved_pass);
		}
		else SingInActivity.putExtra("Saved", false);
		startActivity(SingInActivity);
	
		/*if(!saved_login.equals("") && !saved_pass.equals("") && networkState>0){
			Post post = new Post(this);
		
			String login_res = post.Login(saved_login, saved_pass);		
		
			String[] resultArray = new String[20];
			resultArray = post.processMSG(login_res);
			
			//якщо pc online
			if(resultArray[2].equals("#1")){
				NotifyUtils notify = new NotifyUtils(getApplicationContext(), MenuList.class);
				notify.ShowNotification(Codes.CONNECTION_NOTIFY,
										"З'єднання з сервером встановлено", 
										"PC online", 
										R.drawable.connection_success_small,
										false);	
			}
			else{
				NotifyUtils notify = new NotifyUtils(getApplicationContext(), MenuList.class);
				notify.ShowNotification(Codes.CONNECTION_NOTIFY,
										"З'єднання з сервером встановлено", 
										"PC offline", 
										R.drawable.connection_failed_small,
										false);									
			}
		
			Intent menuActivity = new Intent(getApplicationContext(),MenuList.class);
			startActivity(menuActivity);
		
		}
		else{
			
			Intent SingInActivity = new Intent(getApplicationContext(),SingIn.class);
			startActivity(SingInActivity);			
		}*/
	
		finish();
		
		
	}//onCreate()


	
	/*------------------------------------------------------------------
	//0 - no connection
	//1 - mobile connection
	//2 - wifi connection*/
	private int getNetworkState(){
		try{
			ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeInfo = connectivity.getActiveNetworkInfo();
			boolean isConnected = activeInfo.isConnectedOrConnecting();
			if(isConnected){
				if(activeInfo.getType() == connectivity.TYPE_WIFI){
					return 2;
				}
				return 1;
			}
			return 0;
		}
		catch (Exception e) {
			return 0;
		}
		
	}

}
