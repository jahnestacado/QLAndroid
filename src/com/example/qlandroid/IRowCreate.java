package com.example.qlandroid;

import org.uva.sea.ql.ast.types.Type;

import android.content.Context;

public interface IRowCreate {

	public abstract QLRow createRow(Context context, Type type);
	public abstract QLRow createRow(Context context);
}
