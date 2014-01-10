package com.example.shadowspy;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;

public class CloseAppActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_close_app);
		
		
	    final Handler handler = new Handler() {
	        public void handleMessage(android.os.Message msg) {
	        	if(msg.what == 0){
	        		finish();
	        		NotifyUtils.CancelAllNotify();
	        	}
	        };
	    };
		
		new Thread( new Runnable() {	
			@Override
			public void run() {
		    	SharedPreferences pref = getSharedPreferences(Codes.file_pref, MODE_PRIVATE);
		    	String MyLogin = pref.getString(Codes.saved_login, "");
		    	String MyPass = pref.getString(Codes.saved_pass, "");
				Post post = new Post(getApplicationContext());
				String res = post.Logout(MyLogin, MyPass);
				//TODO
				stopService(new Intent(CloseAppActivity.this, NetService.class));
				handler.sendEmptyMessage(0);
			}
		}).start();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_close_app, menu);
		return true;
	}

}
