package eu.jahnestacado.interpreter;

import org.uva.sea.ql.ast.types.Type;

import android.content.Context;
import eu.jahnestacado.interpreter.rows.CheckBoxRow;
import eu.jahnestacado.interpreter.rows.EditTextRow;
import eu.jahnestacado.interpreter.rows.IntegerRow;
import eu.jahnestacado.interpreter.rows.MoneyRow;
import eu.jahnestacado.interpreter.rows.Row;

public class InputRowFactory extends RowFactory {

	@Override
	public Row createRow(Context context, Type type) {
		if (type.isCompatibleToBoolType()) {
			return new CheckBoxRow(context);
		}
		if (type.isCompatibleToIntType()) {
			return new IntegerRow(context);
		}
		if (type.isCompatibleToStringType()) {
			return new EditTextRow(context);
		}
		if (type.isCompatibleToMoneyType()) {
			return new MoneyRow(context);
		}
		return null;
	}

	@Override
	public Row createRow(Context context) {
		// Is not used for the input rows
		return null;
	}

}
