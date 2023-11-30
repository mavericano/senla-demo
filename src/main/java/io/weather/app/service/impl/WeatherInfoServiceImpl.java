package io.weather.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.weather.app.repository.WeatherInfoRepository;
import io.weather.app.dto.weather.AverageInfoResponse;
import io.weather.app.dto.weather.WeatherInfoDto;
import io.weather.app.entity.WeatherInfoEntity;
import io.weather.app.exception.UnableToFetchException;
import io.weather.app.service.WeatherInfoService;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherInfoServiceImpl implements WeatherInfoService {
    private final WeatherInfoRepository weatherInfoRepository;

    @Value("${weather.api-url}")
    private String weatherApiUrl;
    @Value("${weather.api-key}")
    private String weatherApiKey;
    @Value("${weather.q}")
    private String weatherQuery;
    @Value("${weather.aqi}")
    private String weatherAqi;

    @Override
    public WeatherInfoDto fetchLatest() {
        log.info("Fetching latest at {}", Instant.now());
        var entity = weatherInfoRepository.findFirstByOrderByRequestedAtDesc();
        return entity.toDto();
    }

    @Override
    public AverageInfoResponse fetchAverageForPeriod(Date periodStart, Date periodEnd) {
        //TODO implement
        return null;
    }

    @Retryable(retryFor = {UnableToFetchException.class, RestClientException.class}, maxAttempts = 2,
            backoff = @Backoff(delay = 2000))
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    protected void fetchWeatherInfo() {
        log.info("Fetching weather info from url {}", weatherApiUrl);
        var restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(weatherApiUrl + "?key={weatherApiKey}&q={weatherQuery}&aqi={weatherAqi}",
                 String.class, weatherApiKey, weatherQuery, weatherAqi);

        WeatherInfoEntity weatherInfoEntity = null;
        try {
            weatherInfoEntity = extractWeatherInfo(response).toEntity();
        } catch (JsonProcessingException e) {
            log.error("Error while mapping external API response to WeatherInfoEntity, weather info will not be saved", e);
        }
        if (weatherInfoEntity != null) {
            weatherInfoRepository.save(weatherInfoEntity);
            log.info("Successfully saved weather info to db");
        }
    }

    private WeatherInfoDto extractWeatherInfo(ResponseEntity<String> response) throws JsonProcessingException {
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            log.error("External API responded with code {}, code 200 expected",
                    response.getStatusCode().value());
            throw new UnableToFetchException(String.format("External API responded with code %d, code 200 expected",
                    response.getStatusCode().value()));
        }

        ObjectMapper mapper = new ObjectMapper();
        var root = mapper.readTree(response.getBody());
        var city = root.path("location").path("name").asText();
        var current = root.path("current");
        var tempCel = current.path("temp_c").asDouble();
        var tempFah = current.path("temp_f").asDouble();
        var windKph = current.path("wind_kph").asDouble();
        var pressureMb = current.path("pressure_mb").asDouble();
        var humidity = current.path("humidity").asInt();
        var conditionText = current.path("condition").path("text").asText();
        return new WeatherInfoDto()
            .setTempCelsius(tempCel)
            .setTempFahrenheit(tempFah)
            .setHumidity((byte) humidity)
            .setPressureMillibars(pressureMb)
            .setConditionText(conditionText)
            .setWindSpeedMs(kphToMs(windKph))
            .setCity(city)
            .setRequestedAt(Instant.now());
    }

    private double kphToMs(double kph) {
        return kph / 3.6;
    }
}
