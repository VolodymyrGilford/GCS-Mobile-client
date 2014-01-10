package com.example.shadowspy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	
	//----------------------------------------------------------------
	public MySimpleArrayAdapter(Context context, String[] values) {
		super(context, R.layout.activity_list, values);
		this.context = context;
		this.values = values;
	}

	
	//----------------------------------------------------------------
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.activity_list, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.label);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    textView.setText(values[position]);
	    
	    //встановлює іконки для пунктів меню
	    switch(position){
	    	//ПМ "Відправити повідомлення"
	        case 0:{
		        imageView.setImageResource(R.drawable.mail_blue);	
		        break;
	        }
	        case 1:{
	        	imageView.setImageResource(R.drawable.pref_blue);	
	        	break;
	        	}
	        case 2:{
	        	imageView.setImageResource(R.drawable.logout_blue);
	        	break;
	        }
	        case 3:{
	        	imageView.setImageResource(R.drawable.exit_blue);	
	        	break;
	        }
	    }//switch()	 
	    
	    return rowView;
	}//getView()
	
	
}//Class
