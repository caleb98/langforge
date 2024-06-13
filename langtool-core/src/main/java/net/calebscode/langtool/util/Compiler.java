package net.calebscode.langtool.util;

public abstract class Compiler<T> {

	protected String pattern = "";
	protected int start = 0;
	
	public abstract T compile(String source);
	
	protected final void init(String pattern) {
		this.pattern = pattern;
		start = 0;
	}
	
	protected final boolean isAtEnd() {
		return start >= pattern.length();
	}
	
	protected final char current() {
		return isAtEnd() ? '\0' : pattern.charAt(start);
	}
	
	protected final void skipWhitespace() {
		while (Character.isWhitespace(current())) {
			next();
		}
	}
	
	protected final void next() {
		start++;
	}
	
	protected final boolean test(char match) {
		return current() == match;
	}
	
	protected final boolean match(char match) {
		if (current() == match) {
			next();
			return true;
		}
		
		return false;
	}
	
	protected final void expect(char match, String errorMessage) {
		if (current() != match) {
			error(errorMessage);
		}
		
		next();
	}
	
	protected void error(String message) {
		throw new RuntimeException(String.format(
			"Compilation error at character '%c' (position %d): %s",
			current(), start, message
		));
	}
	
}
