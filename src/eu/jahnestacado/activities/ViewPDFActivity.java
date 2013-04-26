package eu.jahnestacado.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.qlandroid.R;

public class ViewPDFActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionnaire);
		
		
		Intent i = getIntent();
		String val = i.getStringExtra("FILE_NAME");
		
		  Toast.makeText(getApplicationContext(), val, Toast.LENGTH_LONG).show();
	}

}
