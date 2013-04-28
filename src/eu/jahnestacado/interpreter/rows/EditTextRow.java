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

import eu.jahnestacado.R;

import eu.jahnestacado.interpreter.InputValidator;
import eu.jahnestacado.interpreter.RowMargin;
import eu.jahnestacado.interpreter.VariableUpdater;

public class EditTextRow extends Row implements OnClickListener, TextWatcher {
	private final QLLabel label;
	private final QLEditText input;
	private final QLButton button;
	private String varName;
	private Map<String, Value> runTimeValues;
	private VariableUpdater varUpdater;
	private Boolean isInvalidInput;

	public EditTextRow(Context context) {
		super(context);
		label = new QLLabel(context);
		input = new QLEditText(context);
		input.setStrInputType();
		button = new QLButton(context);
		button.setOnClickListener(this);
		input.addTextChangedListener(this);
		this.setBackgroundResource(R.layout.line);
		this.setLayoutParams(RowMargin.getMargin());
	}

	@Override
	public void setSettings(Question qlElement,Map<String, Value> runTimeValues, VariableUpdater varUpdater) {
		String question = qlElement.getLabel().getValue();
		question = question.substring(1, question.length() - 1);
		label.setText(question);
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
