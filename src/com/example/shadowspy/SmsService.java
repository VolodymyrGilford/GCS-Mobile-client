package com.example.shadowspy;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SmsService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		
		return null;
	}
	

	private void showNotification(String text) {
		Toast.makeText(getApplicationContext(), "on", Toast.LENGTH_LONG).show();
	}
	

	public int onStartCommand(Intent intent, int flags, int startId) {
	    String sms_body = intent.getExtras().getString("sms_body");
	    showNotification(sms_body);
	    Log.w("sms", sms_body);
	    return START_STICKY;
	}

}
