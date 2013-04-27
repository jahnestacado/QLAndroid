package eu.jahnestacado.interpreter.rows;

import android.content.Context;
import android.widget.Button;

public class QLButton extends Button {

	public QLButton(Context context) {
		super(context);
		setPreferences();
	}

	private void setPreferences() {
		this.setText("OK");		
	}

}
