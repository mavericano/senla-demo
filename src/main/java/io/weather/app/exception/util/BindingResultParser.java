package io.weather.app.exception.util;

import java.util.StringJoiner;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class BindingResultParser {

    public String getFieldErrMismatches(BindingResult result) {
        StringJoiner sj = new StringJoiner(", ");
        result.getFieldErrors().forEach((err) -> sj.add(err.getField() + ": " + err.getDefaultMessage()));
        return sj.toString();
    }
}
