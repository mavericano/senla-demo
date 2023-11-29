package io.weather.app.dto.weather;

import java.time.Instant;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WeatherInfoDto {
    private Long id;
    private Byte tempCelsius;
    private Short tempFahrenheit;
    private Double windSpeedMs;
    private Short pressureMillibars;
    private Byte humidity;
    private String conditionText;
    private String city;
    private Instant requestedAt;
}
