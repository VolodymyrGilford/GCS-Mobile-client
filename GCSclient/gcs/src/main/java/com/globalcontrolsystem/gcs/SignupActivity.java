package com.globalcontrolsystem.gcs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.globalcontrolsystem.gcs.Const.ApplicationConst;
import com.globalcontrolsystem.gcs.Core.AccessController;
import com.globalcontrolsystem.gcs.DTO.ResponseAddDevice;
import com.globalcontrolsystem.gcs.DTO.Utils;
import com.globalcontrolsystem.gcs.Network.Configuration;
import com.globalcontrolsystem.gcs.Network.NetworkAdapter;
import com.google.gson.*;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends ActionBarActivity {
    private final int CHANGE_CAPTION = 0;
    private Handler signupHandler = null;
    private SignupActivity Instanse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //region status caption handler
        signupHandler = new Handler() {
            public void handleMessage(Message msg) {
                final int what = msg.what;
                switch(what) {
                    case CHANGE_CAPTION :{
                        String text = msg.getData().getString("text");
                        SuccessEvent(text);
                        break;
                    }
                }
            }
        };
        //endregion

        //region confirm button event
        Button confirm_btn = (Button) findViewById(R.id.signup_btn_confirm);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String webKey = ((EditText) findViewById(R.id.signup_edt_webkey)).getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AccessController accessController = AccessController.GetInstanse(getApplicationContext());
                        String deviceKey = accessController.Identificate(webKey);

                        Message handlerMessage = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("text", "Enter this key: " + deviceKey + " on Dashboard.");
                        handlerMessage.setData(bundle);
                        signupHandler.sendMessage(handlerMessage);
                    }
                }).start();
            }
        });
        //endregion

        Instanse = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void SuccessEvent(String text){
        TextView tv = (TextView) findViewById(R.id.signup_tv_caption);
        tv.setText(text);

        ((EditText) findViewById(R.id.signup_edt_webkey)).setVisibility(View.INVISIBLE);

        Button confirm_btn = (Button) findViewById(R.id.signup_btn_confirm);
        confirm_btn.setText("Ok");
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
                startActivity(intent);
                Instanse.finish();
            }
        });
    }
}
