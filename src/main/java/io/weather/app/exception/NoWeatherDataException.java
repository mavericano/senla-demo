package io.weather.app.exception;

public class NoWeatherDataException extends RuntimeException {
    public NoWeatherDataException(String message) {
        super(message);
    }
}
