package org.uva.sea.ql.visitor.checkers;

import java.util.LinkedHashMap;
import java.util.List;

import org.uva.sea.ql.ast.expr.Expr;
import org.uva.sea.ql.ast.expr.Ident;
import org.uva.sea.ql.ast.form.Body;
import org.uva.sea.ql.ast.form.BodyElement;
import org.uva.sea.ql.ast.form.ComputedQuestion;
import org.uva.sea.ql.ast.form.ConditionalElement;
import org.uva.sea.ql.ast.form.Form;
import org.uva.sea.ql.ast.form.IfThen;
import org.uva.sea.ql.ast.form.IfThenElse;
import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.ast.form.SingleLineElement;
import org.uva.sea.ql.ast.types.Type;
import org.uva.sea.ql.visitor.IElementVisitor;
import org.uva.sea.ql.visitor.checkers.error.QLErrorMsg;

public class ElementChecker implements IElementVisitor {
	
	private final LinkedHashMap<String,Type> declaredVar;
	private final List<QLErrorMsg> errorReport;
	 
	
	public ElementChecker(LinkedHashMap<String,Type> declaredVar,List<QLErrorMsg> errorReport){
			this.declaredVar=declaredVar;
			this.errorReport=errorReport;
	}
	
	
	public  boolean check(Form form){
		form.accept(this);
		if(!errorReport.isEmpty()) return false;
		
		return true;
		
	}
	
	private void checkVarName(SingleLineElement qlElement){
		String id=qlElement.getId().getName();
		Type type=qlElement.getType();
		
		if(declaredVar.containsKey(id)){
			addError(new QLErrorMsg("Invalid variable name. Variable '"+id+"' is already declared"));
			return;
		}
		
		declaredVar.put(id,type);
       

	}
	
	private void checkComputedExpr(ComputedQuestion qlElement){
		Expr computedExpr=qlElement.getExpr();
		Type questionType=qlElement.getType();
		Type exprType=computedExpr.getExprType(declaredVar);
		ExpressionChecker.check(computedExpr,declaredVar, errorReport);

		if(!(questionType.isCompatibleToType(exprType))){
			addError(new QLErrorMsg("Invalid expression type. Expression must be of '"+classToString(questionType)+"'."));
			return;
		}
		

	}
	
	
	private static String classToString(Type type){
		String typeToString=type.getClass().getSimpleName();
		return typeToString;
	}
	
	
	private void checkCondition(ConditionalElement qlElement){    
		Expr condition=qlElement.getCondition(); 
		Type conditionType=condition.getExprType(declaredVar);
		ExpressionChecker.check(condition,declaredVar, errorReport);
		if(conditionType.isCompatibleToUndefinedType()){
			 Ident conditionID=(Ident) condition;
			 addError(new QLErrorMsg("Variable '"+conditionID.getName()+"' is undefined."));
		}
		else if(!conditionType.isCompatibleToBoolType()){ 
			addError(new QLErrorMsg("Invalid conditional expression. Expression can only be of 'Boolean' type."));
		}
		
	}
	
	private void acceptIfBodyElement(Body body){
			for(BodyElement element:body.getBodyElement()){
				element.accept(this);
			}
	}
	

	@Override
	public void visit(Form qlElement) {
		qlElement.getBody().accept(this);

	}

	@Override
	public void visit(Body qlElement) {
		List<BodyElement> bodyList=qlElement.getBodyElement();
			for(BodyElement element:bodyList){
				element.accept(this);
			}
			
	}

	@Override
	public void visit(Question qlElement) {
		checkVarName(qlElement);
		
		
	}

	@Override
	public void visit(ComputedQuestion qlElement) {
		checkVarName(qlElement);
		checkComputedExpr(qlElement);
	}

	@Override
	public void visit(IfThenElse qlElement) {
		checkCondition(qlElement);
		acceptIfBodyElement(qlElement.getIfBody());
		acceptIfBodyElement(qlElement.getElseBody());
		
		
	}

	@Override
	public void visit(IfThen qlElement) {
		checkCondition(qlElement);
		acceptIfBodyElement(qlElement.getIfBody());
			
	}
	
   public void printErrors(){
    	for(QLErrorMsg error:errorReport){
    		System.out.println(error.getError());
    	}
    }
    
    private void addError(QLErrorMsg message){
    	errorReport.add(message);
    }
    
    public List<QLErrorMsg> getErrorReport(){
    	return errorReport;
    }
    
    
	
}
