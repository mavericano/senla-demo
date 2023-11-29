package io.weather.app.service.impl;

import io.weather.app.dao.WeatherInfoRepository;
import io.weather.app.dto.weather.AverageInfoResponse;
import io.weather.app.dto.weather.WeatherInfoDto;
import io.weather.app.mapper.WeatherInfoMapper;
import io.weather.app.service.WeatherInfoService;
import java.time.Instant;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherInfoServiceImpl implements WeatherInfoService {
    private final WeatherInfoRepository weatherInfoRepository;
    private final WeatherInfoMapper weatherInfoMapper = WeatherInfoMapper.INSTANCE;

    @Override
    public WeatherInfoDto fetchLatest() {
        log.info("Fetching latest at {}", Instant.now());
        var entity = weatherInfoRepository.findFirstByOrderByRequestedAtDesc();
        return weatherInfoMapper.toDto(entity);
    }

    @Override
    public AverageInfoResponse fetchAverageForPeriod(Date periodStart, Date periodEnd) {
        //TODO implement
        return null;
    }
}
