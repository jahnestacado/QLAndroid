package eu.jahnestacado.interpreter.rows;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.uva.sea.ql.ast.expr.Expr;
import org.uva.sea.ql.ast.form.ComputedQuestion;
import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.visitor.evaluator.ExprEvaluator;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import eu.jahnestacado.interpreter.RowMargin;
import eu.jahnestacado.interpreter.SingleRow;
import eu.jahnestacado.interpreter.VariableUpdater;

public class ComputedRow extends Row implements Observer, SingleRow{

	private final TextView label;
	private final TextView dummyView;
	private final EditText input;
	private Expr expr;
	private String varName;
	private VariableUpdater varUpdater;
	
	public ComputedRow(Context context) {
		super(context);
		label = new TextView(context);
		input = new EditText(context);
		dummyView = new TextView(context);
		dummyView.setVisibility(INVISIBLE);
		//this.setBackgroundResource(R.layout.line);
		this.setLayoutParams(RowMargin.getMargin());
		input.setEnabled(false);
		input.setGravity(Gravity.CENTER);
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
		input.setMinimumWidth(60);
		
		
			

		this.addView(label);
		this.addView(input);
		this.addView(dummyView);
		
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



	@Override
	public String getLabel() {
		return (String) label.getText();
	}



	@Override
	public String getValue() {
		return input.getText().toString();
	}
	
	

}
