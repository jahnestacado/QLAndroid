package eu.jahnestacado.interpreter.rows;

import org.uva.sea.ql.ast.types.Type;

import android.content.Context;

public interface IRowCreate {

	public abstract Row createRow(Context context, Type type);
	public abstract Row createRow(Context context);
}
