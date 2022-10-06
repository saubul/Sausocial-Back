package ru.saubulprojects.sausocial.exception;

public class SausocialException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2778465159197841050L;

	public SausocialException(String eMessage, Exception e) {
		super(eMessage, e);
	}
	
	public SausocialException(String eMessage) {
		super(eMessage);
	}
	
}
