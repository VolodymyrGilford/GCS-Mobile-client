package com.example.shadowspy;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.EditText;


public class StickerView extends EditText {

	public StickerView(Context context) {
		super(context);
		init();
	}
	public StickerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public StickerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		
		
	}
	
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
	}

	





	

}
