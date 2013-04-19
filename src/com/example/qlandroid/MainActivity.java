package com.example.qlandroid;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.uva.sea.ql.ast.form.Form;
import org.uva.sea.ql.ast.types.Type;
import org.uva.sea.ql.parser.antlr.ANTLRParser;
import org.uva.sea.ql.parser.test.ParseError;
import org.uva.sea.ql.visitor.checkers.ElementChecker;
import org.uva.sea.ql.visitor.checkers.error.QLErrorMsg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
		editor.setText("form Android{ " + 	 "qb: \"result input\" boolean  " + '\n'
		+"if(qb){ q2: \"result input\" boolean  "+ '\n'
		+ "q3: \"result input\" int  "+ '\n'+"  }"
		+ "else{q8: \"elsehh\" boolean (q2) "
		+ "q543: \"result input\" int } "
		+ "q8j: \"result input\" money  "
		+  "}");
	}

	@Override
	public void onClick(View arg0) {
		String sourceCode = editor.getText().toString();
		if (sourceCode.length() == 0)
			return;
		Form parsedForm = getParsedForm(sourceCode);
		form = parsedForm;
		// Should catch exception for parse Error*****
		ElementChecker checker = new ElementChecker(
				new LinkedHashMap<String, Type>(), new ArrayList<QLErrorMsg>());
		if (checker.check(parsedForm)) {
			startActivity(new Intent(
					"com.example.qlandroid.QuestionaireActivity"));
		} else {
			Toast.makeText(getApplicationContext(), "FAILED",
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
