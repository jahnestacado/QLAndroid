package eu.jahnestacado.output.pdfgenerator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import eu.jahnestacado.interpreter.rows.ConditionalBodyRow;
import eu.jahnestacado.interpreter.rows.IQLRow;
import eu.jahnestacado.interpreter.rows.SingleRow;

public class OutputState {
	private final Map<String,String> content;
	private static int index;
	private final String formName;
	
	public OutputState(String formName,List<IQLRow> rows) {
		this.formName = formName;
		content = new LinkedHashMap<String,String>();
		index = 1;
		collectData(rows);
	}
	
	private void collectData(List<IQLRow> rows) {
		for (IQLRow row : rows) {
			if (row.isRow()) {
				String label = ((SingleRow) row).getLabel();
				String value = ((SingleRow) row).getValue();
				content.put(index + ". " + label, value);
				index++;
			} else {
				ConditionalBodyRow body = (ConditionalBodyRow) row;
				if (body.isBodyVisible()) {
					collectData(body.getRows());
				}
			}
		}

	}
	
	public Map<String,String> getContent(){
		return content;
	}
	
	public String getFormName(){
		return formName;
	}

}
