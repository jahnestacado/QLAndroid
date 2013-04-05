package com.example.qlandroid;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

public class TextRow extends TableRow implements OnClickListener, Observer{
	private final TextView label;
	private final EditText input;
	private final Button button;
	private String varName;
	private Map<String,Value> runTimeValues;
	private VariableUpdater varUpdater;

	public TextRow(Context context) {
		super(context);
		label = new TextView(context);
		input = new EditText(context);
		button = new Button(context);
		button.setOnClickListener(this);
		button.setText("OK");
	}
	
	public void setSettings(Question qlElement,Map<String,Value> runTimeValues, VariableUpdater varUpdater){
		label.setText(qlElement.getLabel().getValue());
		varName = qlElement.getId().getName();
		this.runTimeValues= runTimeValues;
		this.varUpdater = varUpdater;
		varUpdater.addObserver(this);
		this.addView(label);
		this.addView(input);
		this.addView(button);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
	}

}
