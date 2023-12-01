package io.weather.app.dto.weather;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AverageInfoResponse {
    private Double avgTempCelsius;
    private Double avgTempFahrenheit;
    private Double avgWindSpeedMs;
    private Double avgPressureMillibars;
    private Double avgHumidity;

    public AverageInfoResponse(Double avgTempCelsius, Double avgTempFahrenheit, Double avgWindSpeedMs, Double avgPressureMillibars, Double avgHumidity) {
        this.avgTempCelsius = avgTempCelsius;
        this.avgTempFahrenheit = avgTempFahrenheit;
        this.avgWindSpeedMs = avgWindSpeedMs;
        this.avgPressureMillibars = avgPressureMillibars;
        this.avgHumidity = avgHumidity;
    }
}
