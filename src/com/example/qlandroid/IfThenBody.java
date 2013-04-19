package com.example.qlandroid;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.uva.sea.ql.ast.expr.Expr;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.ExprEvaluator;
import org.uva.sea.ql.visitor.evaluator.values.BoolValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;

public class IfThenBody extends TableLayout implements Observer, IQLRow{
	
	private List<View> rows;
	private  Map<String,Value> runTimeValues;
	private  VariableUpdater varUpdater;
	private  Expr condition;
	

	public IfThenBody(Context context) {
		super(context);
		this.setColumnStretchable(0, true);
		
	}
	
	
	
	public void setSettings(List<View> questionRows,Expr condition,VariableUpdater varUpdater,Map<String,Value> runTimeValues){
		this.rows = questionRows;
		this.condition = condition;
		this.varUpdater = varUpdater;
		this.runTimeValues = runTimeValues;
		this.varUpdater.addObserver(this);
		fillRows();
		setVisibility(runTimeValues);	
	}

	private void fillRows(){
		for(View row : rows){
			this.addView(row);
		}
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		setVisibility(runTimeValues);		
	}
	
	private void setVisibility(Map<String,Value> runTimeValues){
		boolean isVisible=((BoolValue) ExprEvaluator.eval(condition, runTimeValues)).getValue();
		if(isVisible){
		this.setVisibility(VISIBLE);
		return;
		}
		this.setVisibility(GONE);
	}

	



	@Override
	public boolean isRow() {
		return false;
	}

	@Override
	public boolean isBody() {
		return true;
	}



	@Override
	public View getElement() {
		return this;
	}

}