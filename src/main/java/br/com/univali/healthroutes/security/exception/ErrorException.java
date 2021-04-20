package br.com.univali.healthroutes.security.exception;

public class ErrorException extends HealthRoutesException{

	private static final long serialVersionUID = 1L;

	public ErrorException(String message, Throwable e) {
		super(message, e);
	}
	
}
