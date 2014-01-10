package com.example.shadowspy;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;

public class AppPreferenceActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.app_preference);
	}


}
