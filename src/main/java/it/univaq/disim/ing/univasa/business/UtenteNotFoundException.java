package it.univaq.disim.ing.univasa.business;

public class UtenteNotFoundException extends BusinessException {

	public UtenteNotFoundException() {
	}

	// Tipo Throwable identifica la causa dell'eccezione
	public UtenteNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UtenteNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UtenteNotFoundException(String message) {
		super(message);
	}

	public UtenteNotFoundException(Throwable cause) {
		super(cause);
	}

}
