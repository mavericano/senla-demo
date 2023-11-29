package io.weather.app.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    GENERIC_ERROR("error.demo.generic.error"),
    NOT_FOUND("error.demo.not.found"),
    BAD_REQUEST("error.demo.bad.request");

    public final String i18nKey;
}
