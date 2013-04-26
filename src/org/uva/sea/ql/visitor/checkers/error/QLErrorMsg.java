package org.uva.sea.ql.visitor.checkers.error;

public class QLErrorMsg {
	private final String errorMsg;
	
	public QLErrorMsg(String errorMsg){
		this.errorMsg=errorMsg;
	}
	
	public String getError(){
		return errorMsg;
	}

}
