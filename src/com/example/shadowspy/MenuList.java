package com.example.shadowspy;


import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class MenuList extends ListActivity {

	//--------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.w("qqq","10");
		//Підготовка меню
		Resources res = getResources();
		String[] itemsArray = res.getStringArray(R.array.list_items);
		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, itemsArray);
		setListAdapter(adapter);
		Log.w("qqq","11");
		//Запуск інтернет сервісу
		if(!NetService.isWorking){
			Intent netService = new Intent(this, NetService.class);
			Log.w("qqq","12");
			startService(netService);
			Log.w("qqq","13");
		}
	}//onCreate

	
	//--------------------------------------------------------------------------
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Получение элемента, который был нажат
        switch (position) {
	        //ПМ "Відправити повідомлення"
			case 0:{
				Intent sendmsg = new Intent(this, SendMessageActivity.class);
				startActivity(sendmsg);
				break;
			}
			//ПМ "Налаштування"
			case 1:{
				Intent prefScreen = new Intent(this, AppPreferenceActivity.class);
				startActivity(prefScreen);
				break;
			}	
			//ПМ "Вихід"
			case 2:{
				new Thread(new Runnable() {
				    public void run() {
				    	SharedPreferences pref = getSharedPreferences(Codes.file_pref, MODE_PRIVATE);
				    	String MyLogin = pref.getString(Codes.saved_login, "");
				    	String MyPass = pref.getString(Codes.saved_pass, "");
				    	
						Post post = new Post(getApplicationContext());
						String res = post.Logout(MyLogin, MyPass);
						if(!res.equals(Codes.FAIL_LOGOUT)){
							Editor editor = pref.edit();
							editor.putString(Codes.saved_login, "");
							editor.putString(Codes.saved_pass, "");
							editor.putString(Codes.saved_isPCOnline, "#0");
							editor.putString(Codes.saved_isPDAOnline, "#0");
							editor.commit();
							Intent intent = new Intent(getApplicationContext(),SingIn.class);
							startActivity(intent);
							finish();
						}
						else{
							//TODO
						}
				    }//run()
				}).start();
				
				break;
			}
			//ПМ "Закрити"
			case 3:{
				Intent closeAppActivity = new Intent(this, CloseAppActivity.class);
				startActivity(closeAppActivity);
				finish();
				break;
			}

			default:
				break;
		}//switch()
    }//onListItemClick()
	
	
	//-----------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu_list, menu);
		return true;
	}

	
	//----------------------------------------------------
	public void Call(String number){
		Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
		startActivity(call);		
	}
	
	
}
