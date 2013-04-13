package com.example.qlandroid;

import org.uva.sea.ql.ast.types.Type;

import android.content.Context;

public class InputRowFactory extends RowFactory {

	@Override
	public QLRow createRow(Context context, Type type) {
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
	public QLRow createRow(Context context) {
		// Is not used for the input rows
		return null;
	}

}
