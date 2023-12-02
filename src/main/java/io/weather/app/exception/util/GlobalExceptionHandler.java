package io.weather.app.exception.util;

import io.weather.app.dto.error.ErrorResponseBody;
import io.weather.app.dto.error.ErrorType;
import io.weather.app.exception.IncorrectDateException;
import io.weather.app.exception.NoWeatherDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final BindingResultParser bindingResultParser;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoWeatherDataException.class)
    public ErrorResponseBody handleNoData(NoWeatherDataException exception) {
        log.info("Started handling NoWeatherDataException");
        return new ErrorResponseBody()
                .setI18nKey(ErrorType.NO_DATA.getI18nKey())
                .setMessage(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectDateException.class)
    public ErrorResponseBody handleIncorrectDate(IncorrectDateException exception) {
        log.info("Started handling IncorrectDateException");
        return new ErrorResponseBody()
                .setI18nKey(ErrorType.INCORRECT_DATE.getI18nKey())
                .setMessage(exception.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("Started handling MethodArgumentNotValidException");
        BindingResult bindingResult =  ex.getBindingResult();
        String message = "Fields of request dto has errors: " + bindingResultParser.getFieldErrMismatches(bindingResult);
        ErrorResponseBody body = new ErrorResponseBody()
                .setI18nKey(ErrorType.BAD_REQUEST.getI18nKey())
                .setMessage(message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponseBody handleLeftoverException(Exception exception) {
        log.info("Caught exception in leftover handler", exception);
        return new ErrorResponseBody()
                .setI18nKey(ErrorType.GENERIC_ERROR.getI18nKey())
                .setMessage(exception.getMessage());
    }
}
