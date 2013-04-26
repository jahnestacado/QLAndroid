package eu.jahnestacado.activities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.qlandroid.R;
import com.itextpdf.text.DocumentException;

import eu.jahnestacado.interpreter.UIGenerator;
import eu.jahnestacado.interpreter.rows.IQLRow;
import eu.jahnestacado.output.pdfgenerator.OutputState;
import eu.jahnestacado.output.pdfgenerator.QLToPDF;

public class QuestionaireActivity extends Activity implements OnClickListener {

	private Button btn;
	private List<IQLRow> rows;
	private String formName;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.questionnaire);

		TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);
		VariableUpdater varUpdater = new VariableUpdater(
				new LinkedHashMap<String, Value>());
		List<IQLRow> questionRows = new ArrayList<IQLRow>();
		Map<String, Value> declaredVar = new HashMap<String, Value>();

		UIGenerator generator = new UIGenerator(questionRows, varUpdater,
				declaredVar, this);
		generator.generate(MainActivity.getForm());
		rows = generator.getQuestionRows();
		formName = generator.getFormName();
		for (IQLRow row : rows) {
			table.addView(row.getElement());
		}
		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		OutputState state = new OutputState(formName,rows);
		try {
			QLToPDF.generatePdf(state);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/QL/"+formName+".pdf");
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),"application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
		*/
		
		 Intent intent = new Intent(getApplicationContext(),ViewPDFActivity.class);
	        intent.putExtra("FILE_NAME", formName);
	        startActivity(intent);
	        finish();
		
	    Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
	}

	

}
