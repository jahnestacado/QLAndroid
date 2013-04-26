package eu.jahnestacado.interpreter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;

import org.uva.sea.ql.visitor.evaluator.values.Value;


public class VariableUpdater extends Observable{

	private  Map<String,Value> runTimeValues;

	public VariableUpdater(LinkedHashMap<String,Value> linkedHashMap) {
		this.runTimeValues = linkedHashMap;
	}
	
	
	private void update(String varName, Map<String, Value> runTimeValues,Value value){
		runTimeValues.put(varName, value);
		this.runTimeValues = runTimeValues;
	}

	
	public void updateValueAndNotify(String varName, Map<String, Value> runTimeValues,Value value) {
		update(varName,runTimeValues,value);
		informObservers();
	}
	
	public void updateValue(String varName, Map<String, Value> runTimeValues,Value value) {
		update(varName,runTimeValues,value);
	}
	
	
	public void informObservers(){
		setChanged();
		notifyObservers(this);
	}
	
	

	public Map<String,Value> getUpdatedValues() {
		return runTimeValues;
	}
	
	
	

}
