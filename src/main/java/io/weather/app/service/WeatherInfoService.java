package io.weather.app.service;

import io.weather.app.dto.weather.AverageInfoResponse;
import io.weather.app.dto.weather.WeatherInfoDto;
import java.time.LocalDateTime;

public interface WeatherInfoService {
    WeatherInfoDto fetchLatest();
    AverageInfoResponse fetchAverageForPeriod(LocalDateTime periodStart, LocalDateTime periodEnd);
}
