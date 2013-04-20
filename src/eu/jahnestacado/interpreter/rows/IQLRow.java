package eu.jahnestacado.interpreter.rows;

import android.view.View;


public abstract interface IQLRow {

	public boolean isRow();
	
	public boolean isBody();
	
	public View getElement();
	
}

