package eu.jahnestacado.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.qlandroid.R;

import eu.jahnestacado.interpreter.UIGenerator;
import eu.jahnestacado.interpreter.rows.IQLRow;
import eu.jahnestacado.outputstate.ConditionalBodyRow;
import eu.jahnestacado.outputstate.SingleRow;

public class QuestionaireActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	// initialize a button and a counter
	Button btn;
	int counter = 0;
	List<IQLRow> rows;
	List<String> ls = new ArrayList<String>();

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

		for (IQLRow row : rows) {
			table.addView(row.getElement());
		}
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
        ls = new ArrayList<String>();
		fill(rows);
		String tt = "";
		for (String s : ls) {
			tt += s + '\n';
		}
		Toast.makeText(getApplicationContext(), tt, Toast.LENGTH_LONG).show();
	}

	private void fill(List<IQLRow> rows) {
		for (IQLRow row : rows) {
			if (row.isRow()) {
				ls.add(((SingleRow) row).getValue());
			} else {
				ConditionalBodyRow body = (ConditionalBodyRow) row;
				if (body.isBodyVisible()) {
					fill(body.getRows());
				}
			}
		}
	}

}
