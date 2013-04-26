package eu.jahnestacado.activities;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.qlandroid.R;

import eu.jahnestacado.output.pdfgenerator.QLToPDF;

public class ViewPDFActivity extends Activity implements OnClickListener {
	private ImageButton btn;
	private String formName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpdfbutton);
		btn = (ImageButton) findViewById(R.id.button1);
		btn.setOnClickListener(this);
		
		Intent i = getIntent();
		formName = i.getStringExtra("FILE_NAME");
		
		
		  Toast.makeText(getApplicationContext(), formName, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View arg0) {
		File file = new File(QLToPDF.getDir() + "/" + formName + ".pdf");
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),"application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

}
