package io.weather.app.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import io.weather.app.dto.weather.AverageInfoResponse;
import io.weather.app.dto.weather.WeatherInfoDto;
import io.weather.app.entity.WeatherInfoEntity;
import io.weather.app.exception.IncorrectDateException;
import io.weather.app.exception.NoWeatherDataException;
import io.weather.app.repository.WeatherInfoRepository;
import io.weather.app.service.impl.WeatherInfoServiceImpl;
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
    private static final LocalDateTime VALID_PERIOD_START = LocalDateTime.of(2023, 1, 1, 0, 0);
    private static final LocalDateTime VALID_PERIOD_END = LocalDateTime.of(2023, 1, 7, 23, 59);

    @Mock
    private WeatherInfoRepository weatherInfoRepository;
    @InjectMocks
    private WeatherInfoServiceImpl service;

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
                .setRequestedAt(LocalDateTime.parse("2023-11-29T00:00:00"));
        var expected = new WeatherInfoDto()
                .setTempCelsius(1.0)
                .setTempFahrenheit(1.0)
                .setHumidity((byte) 80)
                .setPressureMillibars(1100.0)
                .setConditionText("Snowy")
                .setWindSpeedMs(5.3)
                .setCity("Minsk")
                .setRequestedAt(LocalDateTime.parse("2023-11-29T00:00:00"));
        when(weatherInfoRepository.findFirstByOrderByRequestedAtDesc()).thenReturn(Optional.of(expectedFromDb));

        var actual = service.fetchLatest();

        assertThat(actual, is(expected));

        verify(weatherInfoRepository, times(1)).findFirstByOrderByRequestedAtDesc();
        verifyNoMoreInteractions(weatherInfoRepository);
    }

    @Test
    @DisplayName("Should throw IncorrectDateException when \"periodStart\" is before \"periodEnd\"")
    void testCase03() {
        var invalidPeriodStart = LocalDateTime.of(2023, 1, 10, 0, 0);
        var invalidPeriodEnd = LocalDateTime.of(2023, 1, 5, 23, 59);

        assertThrows(IncorrectDateException.class,
                () -> service.fetchAverageForPeriod(invalidPeriodStart, invalidPeriodEnd));

        verifyNoInteractions(weatherInfoRepository);
    }

    @Test
    @DisplayName("Should throw NoWeatherDataException when there is no data in db for requested period")
    void testCase04() {
        when(weatherInfoRepository.findAllByRequestedAtBetween(VALID_PERIOD_START, VALID_PERIOD_END))
                .thenReturn(new AverageInfoResponse()
                        .setAvgTempCelsius(null)
                        .setAvgTempFahrenheit(null)
                        .setAvgWindSpeedMs(null)
                        .setAvgHumidity(null)
                        .setAvgPressureMillibars(null)
                );

        assertThrows(NoWeatherDataException.class,
                () -> service.fetchAverageForPeriod(VALID_PERIOD_START, VALID_PERIOD_END));

        verify(weatherInfoRepository, times(1)).findAllByRequestedAtBetween(VALID_PERIOD_START, VALID_PERIOD_END);
        verifyNoMoreInteractions(weatherInfoRepository);
    }

    @Test
    @DisplayName("Should return correct rounded average data")
    void testCase05() {
        AverageInfoResponse mockedDbResponse = new AverageInfoResponse()
                .setAvgTempCelsius(10.001)
                .setAvgTempFahrenheit(50.0000)
                .setAvgWindSpeedMs(5.003)
                .setAvgPressureMillibars(1013.037)
                .setAvgHumidity(70.0);
        AverageInfoResponse expectedServiceResponse = new AverageInfoResponse()
                .setAvgTempCelsius(10.0)
                .setAvgTempFahrenheit(50.0)
                .setAvgWindSpeedMs(5.0)
                .setAvgPressureMillibars(1013.04)
                .setAvgHumidity(70.0);
        when(weatherInfoRepository.findAllByRequestedAtBetween(VALID_PERIOD_START, VALID_PERIOD_END)).thenReturn(mockedDbResponse);

        AverageInfoResponse actualServiceResponse = service.fetchAverageForPeriod(VALID_PERIOD_START, VALID_PERIOD_END);

        assertThat(actualServiceResponse, is(expectedServiceResponse));

        verify(weatherInfoRepository, times(1)).findAllByRequestedAtBetween(VALID_PERIOD_START, VALID_PERIOD_END);
        verifyNoMoreInteractions(weatherInfoRepository);
    }
}
