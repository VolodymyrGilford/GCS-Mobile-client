package com.example.shadowspy;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessageActivity extends Activity {
	private EditText etMessage;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_message);
		
		
	    etMessage  = (EditText) findViewById(R.id.et_message);
	    final Handler handlerEtMessage = new Handler() {
	        public void handleMessage(android.os.Message msg) {
	        	switch (msg.what) {
					case 0:{
						etMessage.setText("");
						break;
					}
					case 1:{
						Toast.makeText(getApplicationContext(), "Повідомлення не надіслано!", Toast.LENGTH_SHORT).show();
						break;
					}					
					default:{
						break;
					}
				}//switch()
	        		
	        };//handleMessage()
	        
	    };//handlerEtMessage()
		
	    
        //-------------------------------------------------------
        Button btSendMSG = (Button) findViewById(R.id.bt_send_msg);       
        OnClickListener onlbtSendMSG= new OnClickListener() {
			public void onClick(View v) {
				new Thread(new Runnable() {
				    public void run() {
				    	String MessageText = etMessage.getText().toString();
				    	Post post = new Post(getApplicationContext());
				    	String[] resArray = new String[20];	
				    	
				    	String result = post.SendMessage(Codes.NEW_MSG+"#"+MessageText);
				    	Log.w("Send",result);
						resArray = post.processMSG(result);
						
						if(resArray[1].equals(Codes.FAIL_SEND_MESSAGE)){
								handlerEtMessage.sendEmptyMessage(1);
						}
						else{
							handlerEtMessage.sendEmptyMessage(0);
						}
				    }//run()
				    
				}).start();		
				
			}//onClick()
		};
		btSendMSG.setOnClickListener(onlbtSendMSG);
		
		//-------------------------------------------------------
	    //кнопка відміна
	    Button btCancelSendMSG = (Button) findViewById(R.id.bt_cancel_send_msg);       
	    OnClickListener onlbtCancelSendMSG= new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		};
		btCancelSendMSG.setOnClickListener(onlbtCancelSendMSG);		
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_send_message, menu);
		return true;
	}

}
