package eu.jahnestacado.interpreter.rows;

import java.util.Map;

import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.visitor.evaluator.values.StrValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qlandroid.R;

import eu.jahnestacado.interpreter.InputValidator;
import eu.jahnestacado.interpreter.RowMargin;
import eu.jahnestacado.interpreter.VariableUpdater;

public class EditTextRow extends Row implements OnClickListener, TextWatcher {
	private final TextView label;
	private final EditText input;
	private final Button button;
	private String varName;
	private Map<String, Value> runTimeValues;
	private VariableUpdater varUpdater;
	private Boolean isInvalidInput;

	public EditTextRow(Context context) {
		super(context);
		label = new TextView(context);
		input = new EditText(context);
		button = new Button(context);
		button.setOnClickListener(this);
		button.setText("OK");
		input.addTextChangedListener(this);
		this.setBackgroundResource(R.layout.line);
		this.setLayoutParams(RowMargin.getMargin());
	}

	@Override
	public void setSettings(Question qlElement,Map<String, Value> runTimeValues, VariableUpdater varUpdater) {
		label.setText(qlElement.getLabel().getValue());
		varName = qlElement.getId().getName();
		this.runTimeValues = runTimeValues;
		this.varUpdater = varUpdater;
		this.addView(label);
		this.addView(input);
		this.addView(button);
		input.setMinimumWidth(60);
	}

	@Override
	public void onClick(View v) {
		String value = String.valueOf(input.getText());
		if (isInvalidInput || value.isEmpty())
			return;
		varUpdater.updateValueAndNotify(varName, runTimeValues, new StrValue(value));
	}

	@Override
	public void afterTextChanged(Editable s) {
		String value = input.getText().toString();
		if (!InputValidator.isStringChar(value)) {
			isInvalidInput = true;
			input.setError("String characters expected!");
		} else
			isInvalidInput = false;

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		/*
		 * Is not beign used
		 */

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		/*
		 * Is not beign used
		 */
	}

	@Override
	public boolean isRow() {
		return true;
	}

	@Override
	public boolean isBody() {
		return false;
	}

	@Override
	public View getElement() {
		return this;
	}

}
