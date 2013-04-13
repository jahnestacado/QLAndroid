package com.example.qlandroid;

import java.util.Map;

import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.DecValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MoneyRow extends QLRow implements OnClickListener, TextWatcher{
	private final TextView label;
	private final EditText input;
	private final Button button;
	private String varName;
	private Map<String,Value> runTimeValues;
	private VariableUpdater varUpdater;
	private Boolean isInvalidInput;

	public MoneyRow(Context context) {
		super(context);
		label = new TextView(context);
		input = new EditText(context);
		button = new Button(context);
		button.setOnClickListener(this);
		button.setText("OK");
		input.addTextChangedListener(this);
	}
	
	@Override
	public void setSettings(Question qlElement,Map<String, Value> runTimeValues, VariableUpdater varUpdater) {
		label.setText(qlElement.getLabel().getValue());
		varName = qlElement.getId().getName();
		this.runTimeValues= runTimeValues;
		this.varUpdater = varUpdater;
		this.addView(label);
		this.addView(input);
		this.addView(button);
		input.setMinimumWidth(50);
	}


	@Override
	public void onClick(View v) {
		if (isInvalidInput)
			return;
		float value = Float.valueOf(input.getText().toString());
		varUpdater.updateValueAndNotify(varName, runTimeValues, new DecValue(value));
	}

	@Override
	public void afterTextChanged(Editable s) {
		String value = input.getText().toString();
		if (!InputValidator.isDecimal(value)) {
			isInvalidInput = true;
			input.setError("Numeric value expected!");
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
}
