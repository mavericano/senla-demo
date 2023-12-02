package io.weather.app.dto.weather;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.weather.app.entity.WeatherInfoEntity;
import io.weather.app.mapper.WeatherInfoMapper;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WeatherInfoDto {
    private Long id;
    private Double tempCelsius;
    private Double tempFahrenheit;
    private Double windSpeedMs;
    private Double pressureMillibars;
    private Byte humidity;
    private String conditionText;
    private String city;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime requestedAt;

    public WeatherInfoEntity toEntity() {
        return WeatherInfoMapper.INSTANCE.toEntity(this);
    }
}
