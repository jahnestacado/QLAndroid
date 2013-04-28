package eu.jahnestacado.interpreter.rows;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

public class QLLabel extends TextView {

	public QLLabel(Context context) {
		super(context);
		setPreferences();
	}

	private void setPreferences() {
		this.setTextColor(Color.WHITE);
		this.setWidth(70);
		this.setSingleLine(false);
	}

}
