package com.globalcontrolsystem.gcs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.globalcontrolsystem.gcs.Const.ApplicationConst;
import com.globalcontrolsystem.gcs.Core.AccessController;
import com.google.android.gcm.GCMRegistrar;


public class SplashScreenActivity extends Activity {

    private SplashScreenActivity Instanse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //region GCM
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);

        // Достаем идентификатор регистрации
        final String regId = GCMRegistrar.getRegistrationId(this);

        if (regId.equals("")) { // Если отсутствует, то регистрируемся
            GCMRegistrar.register(this, "428465836571");
        } else {
            Log.v("GCM", "Already registered: " + regId);
        }
        //endregion

        //region Start application
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences settings = getSharedPreferences(ApplicationConst.PREFS_NAME, 0);
        final boolean IsRegistred = settings.getBoolean(ApplicationConst.PREF_IS_REGISTRED, false);

        final AccessController accessController = AccessController.GetInstanse(getApplicationContext());
        if (IsRegistred && !accessController.isAuthenticated){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    accessController.Authenticate();
                }
            }).start();
        }

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
        //endregion
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

}
