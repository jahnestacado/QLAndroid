package eu.jahnestacado.interpreter;

import org.uva.sea.ql.ast.types.Type;

import android.content.Context;
import eu.jahnestacado.interpreter.rows.ComputedRow;
import eu.jahnestacado.interpreter.rows.Row;

public class ComputedRowFactory extends RowFactory {

	@Override
	public Row createRow(Context context, Type type) {
		// Is not beign used for the Computed Rows
		return null;
	}

	@Override
	public Row createRow(Context context) {
		return new ComputedRow(context);
	}

}
