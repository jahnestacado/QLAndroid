package eu.jahnestacado.interpreter.rows;

import java.util.Map;

import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.content.Context;
import android.widget.TableRow;
import eu.jahnestacado.interpreter.VariableUpdater;

public abstract class Row extends TableRow implements IQLRow{

	public Row(Context context) {
		super(context);
	}

	public abstract void setSettings(Question qlElement,
			Map<String, Value> runTimeValues, VariableUpdater varUpdater);

}
