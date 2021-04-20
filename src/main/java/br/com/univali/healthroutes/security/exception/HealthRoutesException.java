package br.com.univali.healthroutes.security.exception;

public abstract class HealthRoutesException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public HealthRoutesException() {
		super();
	}

	public HealthRoutesException(String message) {
		super(message);
	}

	public HealthRoutesException(String message, Throwable t) {
		super(message, t);
	}

}
