package eu.jahnestacado.activities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.uva.sea.ql.ast.form.Form;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.qlandroid.R;
import com.itextpdf.text.DocumentException;

import eu.jahnestacado.interpreter.UIGenerator;
import eu.jahnestacado.interpreter.VariableUpdater;
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
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.questionnaire);
		TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);
		VariableUpdater varUpdater = new VariableUpdater(
				new LinkedHashMap<String, Value>());
		List<IQLRow> questionRows = new ArrayList<IQLRow>();
		Map<String, Value> declaredVar = new HashMap<String, Value>();
		UIGenerator generator = new UIGenerator(questionRows, varUpdater,
				declaredVar, this);
		Intent i = getIntent();
		Form form = (Form) i.getSerializableExtra("FORM");
		generator.generate(form);
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
		OutputState state = new OutputState(formName, rows);
		new AsyncPDFCreation().execute(state);
	}

	private class AsyncPDFCreation extends AsyncTask<OutputState, Void, Void> {

		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			QuestionaireActivity.this.setProgressBarIndeterminateVisibility(true);
			dialog = ProgressDialog.show(QuestionaireActivity.this, "In progress...", "Generating Pdf");
		}

		@Override
		protected Void doInBackground(OutputState... arg0) {
			OutputState state = arg0[0];
			try {
				QLToPDF.generatePdf(state);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			QuestionaireActivity.this.setProgressBarIndeterminateVisibility(true);
			dialog.dismiss();
			Intent intent = new Intent(getApplicationContext(),ViewPDFActivity.class);
			intent.putExtra("FILE_NAME", formName);
			startActivity(intent);
			finish();
			Toast.makeText(getApplicationContext(),"Pdf successfully created!", Toast.LENGTH_LONG).show();

		}

	}

}
