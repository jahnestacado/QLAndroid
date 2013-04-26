package org.uva.sea.ql.ast.expr.binary.bool;

import org.uva.sea.ql.ast.expr.Expr;
import org.uva.sea.ql.ast.expr.binary.Binary;

@SuppressWarnings("serial")
public abstract class Bool extends Binary {

	protected Bool(Expr leftExpr, Expr rightExpr) {
		super(leftExpr, rightExpr);
	}

}
