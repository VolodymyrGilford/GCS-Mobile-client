package com.example.shadowspy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class NotifyUtils {
	private Context context;
	private Intent notificationIntent;
	private static NotificationManager manager;
	private Notification notification;
	
	//--------------------------------------------------------
	public NotifyUtils(Context context, Class cls) {
		this.context = context;
		manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);	
		notificationIntent = new Intent(context, cls);
	}
	
	
	//-----------------------------------------------------------------------
	//Показує повідомлення в Status bar
	public void ShowNotification(int id, String title, String text, int icon, boolean autoCancel){
	    NotificationCompat.Builder nb = new NotificationCompat.Builder(context)
	        .setSmallIcon(icon)
	        .setAutoCancel(autoCancel)
	        .setTicker(title)
	        .setContentText(text)
	        .setContentIntent(PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT))
	        .setWhen(System.currentTimeMillis())
	        .setContentTitle("ShadowSpy")
	        .setDefaults(Notification.DEFAULT_VIBRATE);

			notification = nb.getNotification();        
	        manager.notify(id, notification);     
	}
	
	//----------------------------------------------------------------------
	//Видаляє повідомлення
	public static void CancelAllNotify(){
		manager.cancelAll();
	}
	
	public static void CancelNotify(int id){
		manager.cancel(id);
	}
	
	//----------------------------------------------------------------------
	public void NotifyIntentPutExtra(String key, String value){
		notificationIntent.putExtra(key, value);
	}
	
	
	
}
