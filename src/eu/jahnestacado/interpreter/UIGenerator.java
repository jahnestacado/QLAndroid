package eu.jahnestacado.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uva.sea.ql.ast.expr.Expr;
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

import eu.jahnestacado.interpreter.rows.ElseBody;
import eu.jahnestacado.interpreter.rows.IQLRow;
import eu.jahnestacado.interpreter.rows.IfThenBody;
import eu.jahnestacado.interpreter.rows.Row;

import android.content.Context;
import android.widget.TableRow;

public class UIGenerator implements IElementVisitor{
	private final List<IQLRow> questionRows;
	private final VariableUpdater varUpdater;
	private final Map<String,Value> runTimeValues;
	private final Context context;
	private String formName;
	
	public UIGenerator(List<IQLRow> questionRows, VariableUpdater varUpdater, Map<String,Value> runTimeValues, Context context ){
		this.questionRows=questionRows;
		this.runTimeValues=runTimeValues;
		this.varUpdater=varUpdater;
		this.context = context;
	}
	
	
	public void generate(Form form) {
		formName=form.getId().getName();
		form.accept(this);
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
		Row row = factory.createRow(context, question.getType());
		row.setSettings(question, runTimeValues, varUpdater);
		questionRows.add(row);
	}
	
	@Override
	public void visit(ComputedQuestion question) {
		initVar(question);
		ComputedRowFactory factory = new ComputedRowFactory();
		Row row = factory.createRow(context);
		row.setSettings(question, runTimeValues, varUpdater);
		questionRows.add(row);

	}
	
	@Override
	public void visit(IfThenElse element) {
		Body body = element.getIfBody();
		Expr condition = element.getCondition();
	    List<IQLRow> ifBodyrows =getConditionalBodyElements(body);
	    IfThenBody ifThenBody = new IfThenBody(context);
	    ifThenBody.setSettings(ifBodyrows, condition, varUpdater, runTimeValues);
		questionRows.add(ifThenBody);
		
		body = element.getElseBody();
	    List<IQLRow> elseBodyRows =getConditionalBodyElements(body);
	    ElseBody elseBody = new ElseBody(context);
	    elseBody.setSettings(elseBodyRows, condition, varUpdater, runTimeValues);
		questionRows.add(elseBody);
		
	}
	
	@Override
	public void visit(IfThen element) {
		Body body = element.getIfBody();
		Expr condition = element.getCondition();
	    List<IQLRow> rows =getConditionalBodyElements(body);
	    IfThenBody ifThenBody = new IfThenBody(context);
	    ifThenBody.setSettings(rows, condition, varUpdater, runTimeValues);
		questionRows.add(ifThenBody);
	    
	}
	
	private void fill(Body body){
		body.accept(this);
	}
	
	private List<IQLRow> getConditionalBodyElements(Body qlElement){
		UIGenerator conditionalBodyGeneration = new UIGenerator(new ArrayList<IQLRow>(), varUpdater, runTimeValues, context);
		conditionalBodyGeneration.fill(qlElement);
		return conditionalBodyGeneration.getQuestionRows();
		
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
	
	public List<IQLRow> getQuestionRows(){
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
	
	public String getFormName(){
		return formName;
	}
}
