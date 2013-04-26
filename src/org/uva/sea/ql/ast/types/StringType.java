package org.uva.sea.ql.ast.types;

@SuppressWarnings("serial")
public class StringType extends Type {

	@Override
	public boolean isCompatibleToType(Type type) {
		return type.isCompatibleToStringType();
	}

	@Override
	public boolean isCompatibleToStringType() {
		return true;
	}

}
