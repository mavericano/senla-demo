package io.weather.app.entity;

import io.weather.app.dto.weather.WeatherInfoDto;
import io.weather.app.mapper.WeatherInfoMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "weather_info")
public class WeatherInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //is not used currently, but may be useful when adding new tables in future
    private Double tempCelsius;
    private Double tempFahrenheit;
    private Double windSpeedMs;
    private Double pressureMillibars;
    private Byte humidity;
    private String conditionText;
    private String city;
    private Instant requestedAt;

    public WeatherInfoDto toDto() {
        return WeatherInfoMapper.INSTANCE.toDto(this);
    }
}
