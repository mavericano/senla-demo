package io.weather.app.repository;

import io.weather.app.dto.weather.AverageInfoResponse;
import io.weather.app.entity.WeatherInfoEntity;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherInfoRepository extends CrudRepository<WeatherInfoEntity, Long> {
    Optional<WeatherInfoEntity> findFirstByOrderByRequestedAtDesc();
    @Query("SELECT new io.weather.app.dto.weather.AverageInfoResponse(AVG(w.tempCelsius), AVG(w.tempFahrenheit), AVG(w.windSpeedMs), AVG(w.pressureMillibars), AVG(w.humidity)) " +
            "FROM WeatherInfoEntity w " +
            "WHERE w.requestedAt BETWEEN :requestedAtStart AND :requestedAtEnd")
    AverageInfoResponse findAllByRequestedAtBetween(LocalDateTime requestedAtStart, LocalDateTime requestedAtEnd);
}
