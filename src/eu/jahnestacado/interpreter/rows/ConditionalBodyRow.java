package eu.jahnestacado.interpreter.rows;

import java.util.List;


public interface ConditionalBodyRow {

	public boolean isBodyVisible();
	
	public List<IQLRow> getRows();
}
