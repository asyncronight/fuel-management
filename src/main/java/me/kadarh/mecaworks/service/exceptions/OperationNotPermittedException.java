package me.kadarh.mecaworks.service.exceptions;

public class OperationNotPermittedException extends ApplicationException {

	public OperationNotPermittedException() {
		super();
	}

	public OperationNotPermittedException(String message) {
		super(message);
	}

	public OperationNotPermittedException(String message, Throwable cause) {
		super(message, cause);
	}

	public OperationNotPermittedException(Throwable cause) {
		super(cause);
	}
}
