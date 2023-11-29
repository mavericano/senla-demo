package io.weather.app.dto.error;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ErrorResponseBody {
    private String message;
    private String i18nKey;
}
