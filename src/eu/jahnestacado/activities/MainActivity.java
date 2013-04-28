package eu.jahnestacado.activities;

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
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import eu.jahnestacado.R;

public class MainActivity extends Activity implements OnClickListener {
	private Button runButton;
	private EditText editor;
	private TextView console;
	private Button clean;
	private Button sampleCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		runButton = (Button) findViewById(R.id.button1);
		runButton.setOnClickListener(this);
		clean = (Button) findViewById(R.id.clean);
		clean.setOnClickListener(new Clean());
		sampleCode = (Button) findViewById(R.id.sample);
		sampleCode.setOnClickListener(new SampleCode());
		editor = (EditText) findViewById(R.id.editor);
		console = (TextView) findViewById(R.id.console);
		console.setMovementMethod(ScrollingMovementMethod.getInstance());
		EditText lineCounter = (EditText) findViewById(R.id.linecounter);
		String lines = new String();
		for (int i = 1; i <= 100; i++) {
			lines += i + "." + '\n';
		}
		lineCounter.setText(lines);
	}

	@Override
	public void onClick(View arg0) {
		console.setText("");
		String sourceCode = editor.getText().toString();
		if (sourceCode.length() == 0)
			return;
		Form parsedForm = getParsedForm(sourceCode);
		String currentConsoleDisplay = console.getText().toString();
		if (!currentConsoleDisplay.equals(""))
			return;
		ElementChecker checker = new ElementChecker(
				new LinkedHashMap<String, Type>(), new ArrayList<QLErrorMsg>());

		if (checker.check(parsedForm)) {
			Intent i = new Intent(this, QuestionaireActivity.class);
			i.putExtra("FORM", parsedForm);
			startActivity(i);
		} else {
			displayErrors(checker.getErrorReport());
		}

	}

	public Form getParsedForm(String input) {
		ANTLRParser parser = new ANTLRParser();
		try {
			Form parsedForm = parser.parseForm(input);
			List<String> errorList = parser.getParserErrors();
			if (!errorList.isEmpty()) {
				displayParseErrors(errorList);
			}
			return parsedForm;
		} catch (ParseError e) {
			e.printStackTrace();
		}
		return null;
	}

	public void displayErrors(List<QLErrorMsg> errors) {
		console.setTextColor(Color.MAGENTA);
		String displayedErrors = "TYPE ERRORS:" + '\n';
		for (QLErrorMsg msg : errors) {
			displayedErrors += msg.getError() + '\n';
		}
		console.setText(displayedErrors);
	}

	public void displayParseErrors(List<String> errors) {
		console.setTextColor(Color.CYAN);
		String displayedErrors = "PARSE ERRORS:" + '\n';
		for (String msg : errors) {
			displayedErrors += msg + '\n';
		}
		console.setText(displayedErrors);
	}
	

	
	private class Clean implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			editor.setText("");
		}

	}
	
	public class SampleCode implements OnClickListener {

		@Override
		public void onClick(View v) {
			String sampleCode = getString(R.string.sample_code);
			editor.setText(sampleCode);
		}

	}
}
