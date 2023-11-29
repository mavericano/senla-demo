package io.weather.app.dto.weather;

import lombok.Data;

@Data
public class AverageInfoResponse {
    private Double avgTempCelsius;
    private Double avgTempFahrenheit;
    private Double avgWindSpeedMs;
    private Double avgPressureMillibars;
    private Double avgHumidity;
}
