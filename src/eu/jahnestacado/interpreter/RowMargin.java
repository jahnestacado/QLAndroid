package eu.jahnestacado.interpreter;

import android.widget.TableLayout;

public class RowMargin {
	
	private static int left = 0;
	private static int top = 5;
	private static int right = 0;
	private static int bottom = 5;

	public static TableLayout.LayoutParams getMargin() {
		TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.MATCH_PARENT,
				TableLayout.LayoutParams.WRAP_CONTENT);

		tableRowParams.setMargins(left, top, right, bottom);
		return tableRowParams;
	}

}
