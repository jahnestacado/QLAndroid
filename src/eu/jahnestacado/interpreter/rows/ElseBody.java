package eu.jahnestacado.interpreter.rows;

import java.util.Map;

import org.uva.sea.ql.visitor.evaluator.ExprEvaluator;
import org.uva.sea.ql.visitor.evaluator.values.BoolValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.content.Context;

public class ElseBody extends IfThenBody {

	public ElseBody(Context context) {
		super(context);
	}
	

	@Override
	protected void setVisibility(Map<String, Value> runTimeValues) {
		boolean isVisible = ((BoolValue) ExprEvaluator.eval(condition,
				runTimeValues)).getValue();
		if (isVisible) {
			this.setVisibility(GONE);
			return;
		}
		this.setVisibility(VISIBLE);
	}

}
