package eu.jahnestacado.interpreter.rows;

import android.content.Context;
import android.text.InputType;
import android.widget.EditText;

public class QLEditText extends EditText {

	public QLEditText(Context context) {
		super(context);
		setPreferences();
	}

	private void setPreferences() {
		this.setMinimumWidth(60);
		
	}
	
	public void setNumInputType(){
		this.setRawInputType(InputType.TYPE_CLASS_NUMBER);
	}
	
	public void setStrInputType(){
		this.setRawInputType(InputType.TYPE_CLASS_TEXT);

	}

}
