package eu.jahnestacado.interpreter.rows;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.uva.sea.ql.ast.expr.Expr;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.ExprEvaluator;
import org.uva.sea.ql.visitor.evaluator.values.BoolValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import eu.jahnestacado.outputstate.ConditionalBodyRow;

import android.content.Context;
import android.view.View;
import android.widget.TableLayout;

public class IfThenBody extends TableLayout implements Observer, IQLRow, ConditionalBodyRow{
	
	private List<IQLRow> rows;
	private Map<String, Value> runTimeValues;
	private VariableUpdater varUpdater;
	Expr condition;
	

	public IfThenBody(Context context) {
		super(context);
		this.setColumnStretchable(0, true);
	}
	
	
	public void setSettings(List<IQLRow> questionRows,Expr condition,VariableUpdater varUpdater,Map<String,Value> runTimeValues){
		this.rows = questionRows;
		this.condition = condition;
		this.varUpdater = varUpdater;
		this.runTimeValues = runTimeValues;
		this.varUpdater.addObserver(this);
		fillRows();
		setVisibility(runTimeValues);	
	}

	private void fillRows(){
		for(IQLRow row : rows){
			this.addView(row.getElement());
		}
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		setVisibility(runTimeValues);		
	}
	
	protected void setVisibility(Map<String,Value> runTimeValues){
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


	@Override
	public boolean isBodyVisible() {
		if(getVisibility() == GONE)
		return false;
		
		return true;
	}


	@Override
	public List<IQLRow> getRows() {
		return rows;
	}

}
