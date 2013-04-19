package com.example.qlandroid;

import java.util.Map;

import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.BoolValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class CheckBoxRow extends QLRow implements OnCheckedChangeListener {
	private final TextView label;
	private final TextView dummyView;
	private final CheckBox checkbox;
	private String varName;
	private Map<String,Value> runTimeValues;
	private VariableUpdater varUpdater;

	public CheckBoxRow(Context context) {
		super(context);
		label = new TextView(context);
		checkbox = new CheckBox(context);
		dummyView = new TextView(context);
		dummyView.setVisibility(INVISIBLE);
	}

	@Override
	public void setSettings(Question qlElement,Map<String, Value> runTimeValues, VariableUpdater varUpdater) {
		label.setText(qlElement.getLabel().getValue());
		varName = qlElement.getId().getName();
		this.runTimeValues= runTimeValues;
		this.varUpdater = varUpdater;
		checkbox.setOnCheckedChangeListener(this);		

		this.addView(label);
		this.addView(dummyView);
		this.addView(checkbox);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		varUpdater.updateValueAndNotify(varName, runTimeValues, new BoolValue(checkbox.isChecked()));
		
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
