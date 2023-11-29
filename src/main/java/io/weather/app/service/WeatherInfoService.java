package io.weather.app.service;

import io.weather.app.dto.weather.AverageInfoResponse;
import io.weather.app.dto.weather.WeatherInfoDto;
import java.util.Date;

public interface WeatherInfoService {
    WeatherInfoDto fetchLatest();
    AverageInfoResponse fetchAverageForPeriod(Date periodStart, Date periodEnd);
}
