package com.example.qlandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.uva.sea.ql.ast.form.Form;
import org.uva.sea.ql.ast.types.Type;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.parser.antlr.ANTLRParser;
import org.uva.sea.ql.parser.test.ParseError;
import org.uva.sea.ql.visitor.checkers.ElementChecker;
import org.uva.sea.ql.visitor.checkers.error.QLErrorMsg;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.app.Activity;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Button runButton;
	private EditText editor;
	private static Form form;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		runButton = (Button) findViewById(R.id.button1);
		runButton.setOnClickListener(this);

		editor = (EditText) findViewById(R.id.editText1);
		editor.setText("form Android{ " + "q1 : \"elalalal\" int  " + '\n'
				+ "q3 : \"Nikitis\" int  " + '\n' + "q2 : \"Nikitis\" int  "
				+ '\n' + "q5 : \"Nikitis\" int  " + '\n'
				+ "q6 : \"Nikitis\" int  " + '\n' + "q0 : \"Nikitis\" int  "
				+ '\n' + "q8 : \"Nikitis\" int  " + '\n'
				+ "qg : \"Nikitis\" int  " + '\n' + "qd : \"Nikitis\" int  "
				+ '\n' + "qid : \"Nikitis\" int  " + '\n'
				+ "qs : \"Nikitis\" int  " + '\n' + "q3a : \"Nikitis\" int  "
				+ '\n' + "}");

	}

	@Override
	public void onClick(View arg0) {
		String sourceCode = editor.getText().toString();
		if (sourceCode.length() == 0)
			return;
		Form parsedForm = getParsedForm(sourceCode);
		form = parsedForm;
		ElementChecker checker = new ElementChecker(
				new LinkedHashMap<String, Type>(), new ArrayList<QLErrorMsg>());
		if (checker.check(parsedForm)) {
			startActivity(new Intent(
					"com.example.qlandroid.QuestionaireActivity"));
		} else {
			Toast.makeText(getApplicationContext(), "FAILLED",
					Toast.LENGTH_LONG).show();

		}

	}

	public Form getParsedForm(String input) {
		ANTLRParser parser = new ANTLRParser();
		try {
			Form parsedForm = parser.parseForm(input);
			List<String> errorList = parser.getParserErrors();
			if (!errorList.isEmpty()) {
				Toast.makeText(getApplicationContext(), "Parser_FAILED",
						Toast.LENGTH_LONG).show();
			}
			Toast.makeText(getApplicationContext(), "Parser_OK",
					Toast.LENGTH_LONG).show();
			return parsedForm;
		} catch (ParseError e) {

			e.printStackTrace();
		}
		return null;

	}

	// Must use parcel
	public static Form getForm() {
		return form;
	}

}
