package eu.jahnestacado.interpreter.rows;

import android.content.Context;
import android.widget.EditText;

public class QLEditText extends EditText {

	public QLEditText(Context context) {
		super(context);
		setPreferences();
	}

	private void setPreferences() {
		this.setMinimumWidth(60);
		
	}

}
