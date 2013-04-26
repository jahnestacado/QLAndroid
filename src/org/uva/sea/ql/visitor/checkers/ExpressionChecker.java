package org.uva.sea.ql.visitor.checkers;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.uva.sea.ql.ast.expr.Expr;
import org.uva.sea.ql.ast.expr.Ident;
import org.uva.sea.ql.ast.expr.binary.Binary;
import org.uva.sea.ql.ast.expr.binary.algebraic.Add;
import org.uva.sea.ql.ast.expr.binary.algebraic.Div;
import org.uva.sea.ql.ast.expr.binary.algebraic.Mul;
import org.uva.sea.ql.ast.expr.binary.algebraic.Sub;
import org.uva.sea.ql.ast.expr.binary.bool.And;
import org.uva.sea.ql.ast.expr.binary.bool.Eq;
import org.uva.sea.ql.ast.expr.binary.bool.GEq;
import org.uva.sea.ql.ast.expr.binary.bool.GT;
import org.uva.sea.ql.ast.expr.binary.bool.LEq;
import org.uva.sea.ql.ast.expr.binary.bool.LT;
import org.uva.sea.ql.ast.expr.binary.bool.NEq;
import org.uva.sea.ql.ast.expr.binary.bool.Or;
import org.uva.sea.ql.ast.expr.unary.Neg;
import org.uva.sea.ql.ast.expr.unary.Not;
import org.uva.sea.ql.ast.expr.unary.Pos;
import org.uva.sea.ql.ast.expr.unary.Unary;
import org.uva.sea.ql.ast.expr.values.BoolLit;
import org.uva.sea.ql.ast.expr.values.DecimalLit;
import org.uva.sea.ql.ast.expr.values.IntegerLit;
import org.uva.sea.ql.ast.expr.values.StringLit;
import org.uva.sea.ql.ast.types.Type;
import org.uva.sea.ql.visitor.IExprVisitor;
import org.uva.sea.ql.visitor.checkers.error.QLErrorMsg;




public class ExpressionChecker implements IExprVisitor<Boolean> {
	private final Map<String, Type> declaredVars;
	private final List<QLErrorMsg> errorReport;
	

	private ExpressionChecker(Map<String, Type> declaredVars, List<QLErrorMsg> errorReport) {
		this.declaredVars = declaredVars;
		this.errorReport = errorReport;
	}

	public static boolean check(Expr expr, Map<String, Type> declaredVars,List<QLErrorMsg> errorReport) {
		ExpressionChecker check = new ExpressionChecker(declaredVars, errorReport);
		return expr.accept(check);
	}
	
	private void addError(QLErrorMsg message) {
		errorReport.add(message);
	}


	@Override
	public Boolean visit(Ident node) {
		if (node.getExprType(declaredVars).isCompatibleToUndefinedType()) {
			addError(new QLErrorMsg("Variable '" + node.getName() + "' is undefined."));
			return false;
		}
		return true;
	}
	
//** Binary Exprs	
	@Override
	public Boolean visit(Mul node) {
		if(!checkBranches(node)) return false;
		if(!checkAlgebraicExpr(node, "*")) return false;
		return true;

	}
	
	@Override
	public Boolean visit(Sub node) {
		if(!checkBranches(node)) return false;
		if(!checkAlgebraicExpr(node, "-")) return false;
		return true;

	}
	
	@Override
	public Boolean visit(Add node) {
		if(!checkBranches(node)) return false;
		if(!checkAlgebraicExpr(node, "+")) return false;
		return true;
	}

	
	@Override
	public Boolean visit(Div node) {
		if(!checkBranches(node)) return false;
		if(!checkAlgebraicExpr(node, "/")) return false;
		return true;

	}
	
	//** Logical Exprs	
	@Override
	public Boolean visit(And node) {
		if(!checkBranches(node)) return false;
		if(!checkLogicalExpr(node, "&&")) return false;
        return true;

	}

	@Override
	public Boolean visit(Or node) {
		if(!checkBranches(node)) return false;
		if(!checkLogicalExpr(node, "||")) return false;
        return true;


	}

	//** Comparison Exprs
	@Override
	public Boolean visit(Eq node) {
		if(!checkBranches(node)) return false;
		if(!checkComparisonExpr(node, "==")) return false;
        return true;

	}

	@Override
	public Boolean visit(GEq node) {
		if(!checkBranches(node)) return false;
		if(!checkComparisonExpr(node, ">=")) return false;
		return true;


	}

	@Override
	public Boolean visit(GT node) {
		if(!checkBranches(node)) return false;
		if(!checkComparisonExpr(node, ">")) return false;
		return true;
	}

	
	@Override
	public Boolean visit(LEq node) {
		if(!checkBranches(node)) return false;
		if(!checkComparisonExpr(node, "<=")) return false;
		return true;

	}

	@Override
	public Boolean visit(LT node) {
		if(!checkBranches(node)) return false;
		if(!checkComparisonExpr(node, ">")) return false;
		return true;

	}
	

	
	//** Unary Exprs
	@Override
	public Boolean visit(Neg node) {
		if(!checkOperand(node)) return false;
		if(!isNumericOperand(node,"-")) return false;
		return true;

	}

	@Override
	public Boolean visit(NEq node) {
		if(!checkBranches(node)) return false;
		if(!checkComparisonExpr(node, "!=")) return false;
		return true;


	}

	@Override
	public Boolean visit(Not node) {
		if(!checkOperand(node)) return false;
		if(!isBoolOperand(node,"!")) return false;
		return true;

	}

	

	@Override
	public Boolean visit(Pos node) {
		if(!checkOperand(node)) return false;
		if(!isNumericOperand(node,"+")) return false;
		return true;

	}

    //** Literal Exprs
	@Override
	public Boolean visit(IntegerLit node) {
		return true;

	}

	@Override
	public Boolean visit(BoolLit node) {
		return true;
	}

	@Override
	public Boolean visit(DecimalLit node) {
		return true;

	}

	@Override
	public Boolean visit(StringLit node) {
		return true;

	}
	

	
	//** Exprs Type checks

	private boolean checkBranches(Binary node) {
		boolean leftExpr = node.getLeftExpr().accept(this);
		boolean rightExpr = node.getRightExpr().accept(this);
		if (!(leftExpr && rightExpr)) {
			return false;
		}

		return true;
	}
	
	
	private boolean checkOperand(Unary node) {
		if (!node.getLeftExpr().accept(this)) {
			return false;
		}

		return true;
	}
	
	
	private boolean checkAlgebraicExpr(Binary node,String symbol){
		Type leftExprType = node.getLeftExpr().getExprType(declaredVars);
		Type rightExprType = node.getRightExpr().getExprType(declaredVars);
		Type declaredType=getQuestionsType();
		if (!(leftExprType.isCompatibleToType(rightExprType) && rightExprType.isCompatibleToType(declaredType))) {
		addError(new QLErrorMsg("Invalid type for '"+symbol+"'. Both operands must be of the same Numeric type("+declaredType.getClass().getSimpleName()+")"));
			return false;
		}
		return true;
		
	}
	
	
	private boolean checkLogicalExpr(Binary node,String symbol){
		Type leftExprType = node.getLeftExpr().getExprType(declaredVars);
		Type rightExprType = node.getRightExpr().getExprType(declaredVars);
		if (!(leftExprType.isCompatibleToBoolType() && rightExprType.isCompatibleToBoolType())) {
			errorReport.add(new QLErrorMsg("Invalid type for '"+symbol+"'. Both operands must be of the Boolean type"));
			return false;
		}
		return true;
		
	}
	
	
	private boolean isNumericOperand(Unary node,String symbol){
		Type leftExprType = node.getLeftExpr().getExprType(declaredVars);
		Type declaredType=getQuestionsType();
        if (!leftExprType.isCompatibleToType(declaredType)) {
			errorReport.add(new QLErrorMsg("Invalid type for '"+symbol+"'. Right operand must be of the Numeric type("+declaredType.getClass().getSimpleName()+")"));
			return false;
		}
		return true;
		
	}
	
	
	private boolean isBoolOperand(Unary node,String symbol){
		Type leftExprType = node.getLeftExpr().getExprType(declaredVars);
        if (!leftExprType.isCompatibleToBoolType()) {
			errorReport.add(new QLErrorMsg("Invalid type for '"+symbol+"'. Right operand must be of the Boolean type"));
			return false;
		}
		return true;
		
	}
	
	
	private boolean checkComparisonExpr(Binary node,String symbol){
		Type leftExprType = node.getLeftExpr().getExprType(declaredVars);
		Type rightExprType = node.getRightExpr().getExprType(declaredVars);
		if (!(leftExprType.isCompatibleToType(rightExprType))) {
			errorReport.add(new QLErrorMsg("Invalid type for '"+symbol+"'. Both operands must be of the same type"));
			return false;
		}
		return true;
		
	}
	
	/** Returns the Type of last declared Variable which is the current type of the current processed question
	 *  This way we can check if the type of a numeric expr is the right one
	 */
	
	private Type getQuestionsType() {
		Collection<Type> typeList = declaredVars.values();
		int lastIndex = typeList.size() - 1;
		Type declaredType = (Type) typeList.toArray()[lastIndex];
		return declaredType;
	}

	

}
