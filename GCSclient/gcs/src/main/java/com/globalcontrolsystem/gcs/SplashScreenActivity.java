package com.globalcontrolsystem.gcs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import com.globalcontrolsystem.gcs.Const.ApplicationConst;


public class SplashScreenActivity extends Activity {

    private SplashScreenActivity Instanse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences settings = getSharedPreferences(ApplicationConst.PREFS_NAME, 0);
        final boolean IsRegistred = settings.getBoolean(ApplicationConst.PREF_IS_REGISTRED, false);

        //Delay 3 sec. before start main activity
        final SplashScreenActivity current = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (IsRegistred)
                    startActivity(new Intent(current, MainScreenActivity.class));
                else
                    startActivity(new Intent(current, SignupActivity.class));

                Instanse.finish();
            }
        }, 2000);

        Instanse = this;
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

}
