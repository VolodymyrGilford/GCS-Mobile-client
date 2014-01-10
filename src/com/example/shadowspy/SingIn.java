package com.example.shadowspy;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SingIn extends Activity {
	private TextView textProcess;
    private EditText etLogin;
    private EditText etPass;
    public SharedPreferences preference;

	//---------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sing_in);
		
		
		etLogin = (EditText) findViewById(R.id.edit_login);
		etPass = (EditText) findViewById(R.id.edit_password);
		
		Boolean isSaved = getIntent().getExtras().getBoolean("Saved");
		if (isSaved){
			String login = getIntent().getExtras().getString("login");
			String pass = getIntent().getExtras().getString("pass");
			etLogin.setText(login);
			etPass.setText(pass);
		}
	
		
		//ініціальзація для роботи з налаштуваннями програми
		preference = getSharedPreferences(Codes.file_pref, MODE_PRIVATE);
	
	
	
		//отримуємо доступ до елементів управління	
		textProcess = (TextView) findViewById(R.id.res);
	    final Handler handlerTextProcess = new Handler() {
	        public void handleMessage(android.os.Message msg) {
	        	switch (msg.what) {
				case 0:{
					textProcess.setText("Зачекайте будь ласка!");
					break;
				}
				case 1:{
					textProcess.setText("Реєстрація успішна! Авторизуйтесь!");
					break;
				}	
				case 2:{
					textProcess.setText("Помилка реєстрації!");
					break;
				}	
				case 3:{
					textProcess.setText("Успішна авторизація!");
					
					break;
				}
				case 4:{
					textProcess.setText("Невірний логін!");
					break;
				}
				case 5:{
					textProcess.setText("Невірний пароль!");
					break;
				}
				case 6:{
					textProcess.setText("Немає з'єднання!");
					break;
				}				
				default:
					break;
				}
	        		
	        };
	    };
	    
	    
	    final Handler handlerEtLogin = new Handler() {
	        public void handleMessage(android.os.Message msg) {
	        	if(msg.what == 1)
	        		etLogin.setText("");
	        };
	    };	    
	    
	       
	    final Handler handlerEtPass = new Handler() {
	        public void handleMessage(android.os.Message msg) {
	        	if(msg.what == 1)
	        		etPass.setText("");
	        };
	    };	    
    
	    
        //-------------------------------------------------------
        //кнопка вхід
        Button btLogin = (Button) findViewById(R.id.bt_login);       
        OnClickListener onlBtLogin = new OnClickListener() {
			public void onClick(View v) {
				new Thread(new Runnable() {
				    public void run() {
						//авторизаційні дані
						handlerTextProcess.sendEmptyMessage(0);
						String login = etLogin.getText().toString();
						String pass = etPass.getText().toString();		
						
						//запит на авторизацію
						Post post = new Post(getApplicationContext());
						String login_res = post.Login(login, pass);	
						//login_res ="#25#1";
						String[] resultArray = new String[20];
						resultArray = post.processMSG(login_res);

						String id = resultArray[1];
						//обробка результату
						if(id.equals(Codes.SUCCESS_AUTHORIZATION)){
							//TODO
							handlerEtLogin.sendEmptyMessage(1);
							handlerEtPass.sendEmptyMessage(1);
							handlerTextProcess.sendEmptyMessage(3);
							
							Editor editor = preference.edit();
							editor.putString(Codes.saved_login, login);
							editor.putString(Codes.saved_pass, pass);
							editor.putString(Codes.saved_isPCOnline, resultArray[2]);
							editor.putString(Codes.saved_isPDAOnline, "#1");
							editor.commit();
							
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
							finish();
						}
						if(id.equals(Codes.BAD_LOGIN)){
							handlerEtLogin.sendEmptyMessage(1);
							handlerTextProcess.sendEmptyMessage(4);	
						}
						if(id.equals(Codes.BAD_PASSWORD)){
							handlerEtPass.sendEmptyMessage(1);
							handlerTextProcess.sendEmptyMessage(5);
						}
						if(id.equals(Codes.NO_CONNECT)){
							handlerTextProcess.sendEmptyMessage(6);
						}
						
				    }//run()
				}).start();				
			}
		};
		btLogin.setOnClickListener(onlBtLogin);

		
        //-------------------------------------------------------
        //кнопка реєстрація
        final Button btRegistration = (Button) findViewById(R.id.bt_registration);       
        OnClickListener onlBtRegistration = new OnClickListener() {
			public void onClick(View v) {			
			    new Thread(new Runnable() {
			        public void run() {
						//реєстраційні дані
						handlerTextProcess.sendEmptyMessage(0);
						String login = etLogin.getText().toString();
						String pass = etPass.getText().toString();
						//запит на реєстрацію
						Post post = new Post(getApplicationContext());
						String resultMSG = post.Registration(login, pass);
						
						String[] resArray = new String[2];
						resArray = post.processMSG(resultMSG);	
						
						String id = resArray[1];
						//обробка результату
						if(id.equals(Codes.SUCCESS_REGISTRATION)){
							//TODO
							
							handlerEtLogin.sendEmptyMessage(1);
							handlerEtPass.sendEmptyMessage(1);
							handlerTextProcess.sendEmptyMessage(1);	
							
							Editor editor = preference.edit();
							editor.putString(Codes.saved_login, login);
							editor.putString(Codes.saved_pass, pass);
							editor.commit();
							
						}
						if(id.equals(Codes.FAIL_REGISTRATION)){
							//TODo
							handlerTextProcess.sendEmptyMessage(2);
						}
						if(id.equals(Codes.NO_CONNECT)){
							handlerTextProcess.sendEmptyMessage(6);
						}
			        }
			    }).start();					
			}
		};
		btRegistration.setOnClickListener(onlBtRegistration);
		
		
    }


	
	
	//----------------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sing_in, menu);
		return true;
	}
	
	


	

}
