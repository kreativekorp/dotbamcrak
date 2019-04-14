package com.kreative.dotbamcrak.utility;

public class ParseException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private int lineNumber;
	
	public ParseException() {
		super();
		this.lineNumber = 0;
	}
	
	public ParseException(Throwable cause) {
		super(cause);
		this.lineNumber = 0;
	}
	
	public ParseException(String message) {
		super(message);
		this.lineNumber = 0;
	}
	
	public ParseException(String message, Throwable cause) {
		super(message, cause);
		this.lineNumber = 0;
	}
	
	public ParseException(String message, int lineNumber) {
		super(message + " on line " + lineNumber + ".");
		this.lineNumber = lineNumber;
	}
	
	public ParseException(String message, int lineNumber, Throwable cause) {
		super(message + " on line " + lineNumber + ".", cause);
		this.lineNumber = lineNumber;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
}
