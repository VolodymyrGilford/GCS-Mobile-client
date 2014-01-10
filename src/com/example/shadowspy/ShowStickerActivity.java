package com.example.shadowspy;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class ShowStickerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sticker);
		
		Intent intentData = getIntent();
		String text = intentData.getStringExtra("sticker_text");
		TextView tv = (TextView) findViewById(R.id.sticker_text_view);
		tv.setText(text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_show_sticker, menu);
		return true;
	}

}
