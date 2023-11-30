package io.weather.app.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import io.weather.app.dto.weather.WeatherInfoDto;
import io.weather.app.entity.WeatherInfoEntity;
import io.weather.app.exception.NoWeatherDataException;
import io.weather.app.repository.WeatherInfoRepository;
import io.weather.app.service.impl.WeatherInfoServiceImpl;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WeatherInfoServiceImplTest {
    @Mock
    WeatherInfoRepository weatherInfoRepository;
    @InjectMocks
    WeatherInfoServiceImpl service;

    @Test
    @DisplayName("Should throw NoWeatherDataException if db is empty")
    void testCase01() {
        when(weatherInfoRepository.findFirstByOrderByRequestedAtDesc()).thenReturn(Optional.empty());

        assertThrows(NoWeatherDataException.class, () -> service.fetchLatest());

        verify(weatherInfoRepository, times(1)).findFirstByOrderByRequestedAtDesc();
        verifyNoMoreInteractions(weatherInfoRepository);
    }

    @Test
    @DisplayName("Should return latest info")
    void testCase02() {
        var expectedFromDb = new WeatherInfoEntity()
                .setTempCelsius(1.0)
                .setTempFahrenheit(1.0)
                .setHumidity((byte) 80)
                .setPressureMillibars(1100.0)
                .setConditionText("Snowy")
                .setWindSpeedMs(5.3)
                .setCity("Minsk")
                .setRequestedAt(LocalDateTime.parse("2023-11-29T00:00:00Z"));
        var expected = new WeatherInfoDto()
                .setTempCelsius(1.0)
                .setTempFahrenheit(1.0)
                .setHumidity((byte) 80)
                .setPressureMillibars(1100.0)
                .setConditionText("Snowy")
                .setWindSpeedMs(5.3)
                .setCity("Minsk")
                .setRequestedAt(LocalDateTime.parse("2023-11-29T00:00:00Z"));
        when(weatherInfoRepository.findFirstByOrderByRequestedAtDesc()).thenReturn(Optional.of(expectedFromDb));

        var actual = service.fetchLatest();

        assertThat(actual, is(expected));

        verify(weatherInfoRepository, times(1)).findFirstByOrderByRequestedAtDesc();
        verifyNoMoreInteractions(weatherInfoRepository);
    }
}
