package eu.jahnestacado.interpreter.rows;

import android.widget.TableLayout;

public class RowMargin {
	
	private final static int left = 0;
	private final static int top = 5;
	private final static int right = 0;
	private final static int bottom = 5;

	public static TableLayout.LayoutParams getMargin() {
		TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.MATCH_PARENT,
				TableLayout.LayoutParams.WRAP_CONTENT);

		tableRowParams.setMargins(left, top, right, bottom);
		return tableRowParams;
	}

}
