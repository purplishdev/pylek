package me.michalwozniak.pylek.api;

public class ApiServiceException extends RuntimeException {

    public ApiServiceException() {
    }

    public ApiServiceException(String message) {
        super(message);
    }

    public ApiServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiServiceException(Throwable cause) {
        super(cause);
    }
}
