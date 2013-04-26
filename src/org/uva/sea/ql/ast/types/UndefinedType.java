package org.uva.sea.ql.ast.types;

@SuppressWarnings("serial")
public class UndefinedType extends Type {

	@Override
	public boolean isCompatibleToType(Type type) {
		return type.isCompatibleToUndefinedType();
	}

	@Override
	public boolean isCompatibleToUndefinedType() {
		return true;
	}

}
