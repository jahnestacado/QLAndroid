package org.uva.sea.ql.parser.antlr;

import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.uva.sea.ql.ast.expr.Expr;
import org.uva.sea.ql.ast.form.Body;
import org.uva.sea.ql.ast.form.ComputedQuestion;
import org.uva.sea.ql.ast.form.ConditionalElement;
import org.uva.sea.ql.ast.form.Form;
import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.ast.types.Type;
import org.uva.sea.ql.parser.error.IParse;
import org.uva.sea.ql.parser.error.ParseError;


public class ANTLRParser implements IParse {
	private  List<String> parserErrors;


	public QLParser parse(String src) throws ParseError {
		ANTLRStringStream stream = new ANTLRStringStream(src);
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(new QLLexer(stream));
		QLParser parser = new QLParser(tokens);
		parserErrors = parser.getErrors();
		return parser;
	}


	@Override
	public Expr parseExpr(String src) throws ParseError{
		 try {
			return parse(src).orExpr();
		} catch (RecognitionException e) {
			throw new ParseError(e.getMessage());
		}
	}


	@Override
	public Form parseForm(String src) throws ParseError{
		 try {
			return parse(src).form();
		} catch (RecognitionException e) {
			throw new ParseError(e.getMessage());
		}
	}


	@Override
	public Type parseType(String src) throws ParseError{
		 try {
			return parse(src).type();
		} catch (RecognitionException e) {
			throw new ParseError(e.getMessage());
		}
	}

	@Override
	public Question parseQuestion(String src) throws ParseError{
		 try {
			return parse(src).question();
		} catch (RecognitionException e) {
			throw new ParseError(e.getMessage());
		}
	}

	@Override
	public ComputedQuestion parseComputedQuestion(String src) throws ParseError{
		 try {
			return parse(src).computedQuestion();
		} catch (RecognitionException e) {
			throw new ParseError(e.getMessage());
		}
	}

	@Override
	public ConditionalElement parseConditionalElement(String src) throws ParseError{
		 try {
			return parse(src).conditionalElement();
		} catch (RecognitionException e) {
			throw new ParseError(e.getMessage());
		}
	}

	@Override
	public Body parseBody(String src) throws ParseError{
		 try {
			return parse(src).body();
		} catch (RecognitionException e) {
			throw new ParseError(e.getMessage());
		}
	}
	
	public List<String> getParserErrors(){
		return parserErrors;
	}



}