package com.globalcontrolsystem.gcs;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
import com.globalcontrolsystem.gcs.DTO.Response;
import com.globalcontrolsystem.gcs.Network.Configuration;
import com.globalcontrolsystem.gcs.Network.NetworkAdapter;
import com.globalcontrolsystem.gcs.Services.GPStracker;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class MainScreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        GPStracker.GetInstance(getApplicationContext());

        Button btn = (Button) findViewById(R.id.main_btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences settings = getSharedPreferences(ApplicationConst.PREFS_NAME, 0);
                        String message = ((EditText) findViewById(R.id.main_edt_msg)).getText().toString();

                        AccessController accessController = AccessController.GetInstanse(getApplicationContext());

                        JsonObject data = new JsonObject();
                        data.addProperty("token", accessController.GetToken());
                        data.addProperty("text", message);

                        String json = new NetworkAdapter().Send(Configuration.MakeSendMsgUrl(), data);

                        Response response = new Gson().fromJson(json, Response.class);
                        if (response.error != null){
                            //Show message
                        }

                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:{
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);
            return rootView;
        }
    }

}
