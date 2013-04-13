package com.example.qlandroid;

import org.uva.sea.ql.ast.types.Type;

import android.content.Context;

public class ComputedRowFactory extends RowFactory {

	@Override
	public QLRow createRow(Context context, Type type) {
		// Is not beign used for the Computed Rows
		return null;
	}

	@Override
	public QLRow createRow(Context context) {
		return new ComputedRow(context);
	}

}
