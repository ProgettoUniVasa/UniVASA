package it.univaq.disim.oop.pharma.view;

public class ViewException extends Exception {

	public ViewException() {
		super();
	}

	// Tipo Throwable identifica la causa dell'eccezione
	public ViewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ViewException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViewException(String message) {
		super(message);
	}

	public ViewException(Throwable cause) {
		super(cause);
	}

}
