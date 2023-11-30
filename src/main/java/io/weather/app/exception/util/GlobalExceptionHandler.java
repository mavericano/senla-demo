package io.weather.app.exception.util;

import io.weather.app.dto.error.ErrorResponseBody;
import io.weather.app.dto.error.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final BindingResultParser bindingResultParser;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult bindingResult =  ex.getBindingResult();
        String message = "Fields of request dto has errors: " + bindingResultParser.getFieldErrMismatches(bindingResult);
        ErrorResponseBody body = new ErrorResponseBody()
                .setI18nKey(ErrorType.BAD_REQUEST.getI18nKey())
                .setMessage(message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponseBody handleLeftoverException(Exception exception) {
        log.warn("Started handling leftover exception");
        return new ErrorResponseBody()
                .setI18nKey(ErrorType.GENERIC_ERROR.getI18nKey())
                .setMessage(exception.getMessage());
    }
}
