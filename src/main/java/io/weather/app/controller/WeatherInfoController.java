package io.weather.app.controller;

import io.weather.app.dto.weather.AverageInfoResponse;
import io.weather.app.dto.weather.TimePeriodRequest;
import io.weather.app.dto.weather.WeatherInfoDto;
import io.weather.app.service.WeatherInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherInfoController {
    private final WeatherInfoService weatherInfoService;

    @GetMapping("/latest")
    public WeatherInfoDto fetchLatest() {
        return weatherInfoService.fetchLatest();
    }

    @PostMapping("/average")
    public AverageInfoResponse fetchAverageForPeriod(@RequestBody TimePeriodRequest period) {
        return weatherInfoService.fetchAverageForPeriod(period.getFrom(), period.getTo());
    }
}
