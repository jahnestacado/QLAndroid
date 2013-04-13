package com.example.qlandroid;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.uva.sea.ql.ast.expr.Expr;
import org.uva.sea.ql.ast.form.ComputedQuestion;
import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.ExprEvaluator;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

public class ComputedRow extends QLRow implements Observer{

	private final TextView label;
	private final EditText input;
	private Expr expr;
	private String varName;
	private VariableUpdater varUpdater;
	
	public ComputedRow(Context context) {
		super(context);
		label = new TextView(context);
		input = new EditText(context);
	}
	
	

	@Override
	public void update(Observable observable, Object data) {
		Map<String, Value> currentValues = varUpdater.getUpdatedValues();
		Value newVal = ExprEvaluator.eval(expr, currentValues);
		varUpdater.updateValue(varName, currentValues, newVal);
		input.setText(String.valueOf(newVal.getValue()));
	}


	@Override
	public void setSettings(Question qlElement,Map<String, Value> runTimeValues, VariableUpdater varUpdater) {
		ComputedQuestion question = (ComputedQuestion) qlElement;
		expr = question.getExpr();
		Value initValue = ExprEvaluator.eval(expr, runTimeValues);
		input.setText(String.valueOf(initValue.getValue()));
		label.setText(qlElement.getLabel().getValue());
		varName = qlElement.getId().getName();
		this.varUpdater = varUpdater;
		varUpdater.addObserver(this);
		this.addView(label);
		this.addView(input);
		
	}

}
