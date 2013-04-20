package eu.jahnestacado.outputstate;

import java.util.List;

import eu.jahnestacado.interpreter.rows.IQLRow;

public interface ConditionalBodyRow {

	public boolean isBodyVisible();
	
	public List<IQLRow> getRows();
}
