package com.example.qlandroid;

import java.util.List;
import java.util.Map;

import org.uva.sea.ql.ast.form.Body;
import org.uva.sea.ql.ast.form.BodyElement;
import org.uva.sea.ql.ast.form.ComputedQuestion;
import org.uva.sea.ql.ast.form.Form;
import org.uva.sea.ql.ast.form.IfThen;
import org.uva.sea.ql.ast.form.IfThenElse;
import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.ast.form.SingleLineElement;
import org.uva.sea.ql.ast.types.Type;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.IElementVisitor;
import org.uva.sea.ql.visitor.evaluator.values.BoolValue;
import org.uva.sea.ql.visitor.evaluator.values.DecValue;
import org.uva.sea.ql.visitor.evaluator.values.IntValue;
import org.uva.sea.ql.visitor.evaluator.values.StrValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.content.Context;
import android.widget.TableRow;

public class UIGenerator implements IElementVisitor{
	private final List<TableRow> questionRows;
	private final VariableUpdater varUpdater;
	private final Map<String,Value> runTimeValues;
	private final Context context;
	
	public UIGenerator(List<TableRow> questionRows, VariableUpdater varUpdater, Map<String,Value> runTimeValues, Context context ){
		this.questionRows=questionRows;
		this.runTimeValues=runTimeValues;
		this.varUpdater=varUpdater;
		this.context = context;
		
	}
	
	
	public void generate(Form form) {
		String formName=form.getId().getName();
		//frame.setTitle(formName);
		form.accept(this);
		//new Renderer(questionPanelList, frame,varUpdater).addQuestionsToPanel();

	}
	
	
	@Override
	public void visit(Form form) {
		form.getBody().accept(this);
		
	}
	@Override
	public void visit(Body body) {
		List<BodyElement> bodyList = body.getBodyElement();
		for (BodyElement element : bodyList) {
			element.accept(this);
		}
		
	}
	@Override
	public void visit(Question question) {
		initVar(question);
		InputRowFactory factory = new InputRowFactory(); 
		QLRow row = factory.createRow(context, question.getType());
		row.setSettings(question, runTimeValues, varUpdater);
		questionRows.add(row);
	}
	
	@Override
	public void visit(ComputedQuestion question) {
		initVar(question);
		ComputedRowFactory factory = new ComputedRowFactory();
		QLRow row = factory.createRow(context);
		row.setSettings(question, runTimeValues, varUpdater);
		questionRows.add(row);

	}
	@Override
	public void visit(IfThenElse arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfThen arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void initVar(SingleLineElement qlElement){
		String varName=qlElement.getId().getName();
		if(runTimeValues.containsKey(varName)){
			Value value=runTimeValues.get(varName);
			runTimeValues.put(varName, value);
		}
		else runTimeValues.put(varName,initValue(qlElement));
		
	}
	
	
	private Value initValue(SingleLineElement qlElement) {
		Type type = qlElement.getType();

		if (type.isCompatibleToBoolType()) {
			return new BoolValue(false);
		} else if (type.isCompatibleToIntType()) {
			return new IntValue(0);
		} else if (type.isCompatibleToMoneyType()) {
			return new DecValue(0.0f);
		} else {
			return new StrValue("");
		}

	}
	
	public List<TableRow> getQuestionRows(){
		return questionRows;
	}

	
	public static TableRow[] toArray(List<TableRow> questionRows) {
		int size = questionRows.size();
		TableRow[] rows = new TableRow[size];
		for (int i = 0; i <= size - 1; i++) {
			rows[i] = questionRows.get(i);
		}
		return rows;
	}
}
