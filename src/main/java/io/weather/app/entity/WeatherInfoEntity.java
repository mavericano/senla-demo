package io.weather.app.entity;

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
    private Byte tempCelsius;
    private Short tempFahrenheit;
    private Double windSpeedMs;
    private Short pressureMillibars;
    private Byte humidity;
    private String conditionText;
    private String city;
    private Instant requestedAt;
}
