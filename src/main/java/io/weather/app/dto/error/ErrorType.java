package io.weather.app.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    GENERIC_ERROR("error.weather.generic.error"),
    NO_DATA("error.weather.no.data"),
    INCORRECT_DATE("error.weather.incorrect.date"),
    BAD_REQUEST("error.weather.bad.request");

    public final String i18nKey;
}
