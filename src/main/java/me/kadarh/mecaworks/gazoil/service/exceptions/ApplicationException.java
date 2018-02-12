package me.kadarh.mecaworks.gazoil.service.exceptions;

public class ApplicationException extends RuntimeException {

    ApplicationException() {
        super();
    }

    ApplicationException(String message) {
        super(message);
    }

    ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    ApplicationException(Throwable cause) {
        super(cause);
    }
}
