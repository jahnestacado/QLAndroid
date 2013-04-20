package eu.jahnestacado.interpreter;

public class InputValidator {

	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean isStringChar(String input) {
		return !input.matches(".*\\d.*");
	}

	public static boolean isDecimal(String input) {
		try {
			Float.parseFloat(input);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
