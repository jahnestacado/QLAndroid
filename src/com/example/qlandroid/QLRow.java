package com.example.qlandroid;

import java.util.Map;

import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.content.Context;
import android.widget.TableRow;

public abstract class QLRow extends TableRow {

	public QLRow(Context context) {
		super(context);
	}

	public abstract void setSettings(Question qlElement,
			Map<String, Value> runTimeValues, VariableUpdater varUpdater);

}
