package io.weather.app.exception;

public class UnableToFetchException extends RuntimeException {
    public UnableToFetchException(String message) {
        super(message);
    }
}
