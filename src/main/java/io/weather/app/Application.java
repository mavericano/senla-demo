package io.weather.app;

import io.weather.app.dao.WeatherInfoRepository;
import io.weather.app.entity.WeatherInfoEntity;
import java.time.Instant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
//TODO remove debug runner
//	@Bean
//	CommandLineRunner run(WeatherInfoRepository repository) {
//        return args -> {
//            repository.save(new WeatherInfoEntity()
//					.setTempCelsius((byte)1)
//					.setTempFahrenheit((short)1)
//					.setHumidity((byte)80)
//					.setPressureMillibars((short)1100)
//					.setConditionText("Snowy")
//					.setWindSpeedMs(5.3)
//					.setCity("Minsk")
//					.setRequestedAt(Instant.parse("2023-11-29T00:00:00Z"))
//			);
//			repository.save(new WeatherInfoEntity()
//					.setTempCelsius((byte)1)
//					.setTempFahrenheit((short)1)
//					.setHumidity((byte)80)
//					.setPressureMillibars((short)1100)
//					.setConditionText("Snowy")
//					.setWindSpeedMs(5.3)
//					.setCity("Minsk")
//					.setRequestedAt(Instant.parse("2023-11-28T07:00:00Z"))
//			);
//			repository.save(new WeatherInfoEntity()
//						.setTempCelsius((byte)1)
//						.setTempFahrenheit((short)1)
//						.setHumidity((byte)80)
//						.setPressureMillibars((short)1100)
//						.setConditionText("Snowy")
//						.setWindSpeedMs(5.3)
//						.setCity("Minsk")
//						.setRequestedAt(Instant.parse("2023-11-29T02:00:00Z"))
//				);
//			repository.save(new WeatherInfoEntity()
//						.setTempCelsius((byte)1)
//						.setTempFahrenheit((short)1)
//						.setHumidity((byte)80)
//						.setPressureMillibars((short)1100)
//						.setConditionText("Snowy")
//						.setWindSpeedMs(5.3)
//						.setCity("Minsk")
//						.setRequestedAt(Instant.parse("2023-11-29T02:00:00Z"))
//				);
//			repository.save(new WeatherInfoEntity()
//						.setTempCelsius((byte)1)
//						.setTempFahrenheit((short)1)
//						.setHumidity((byte)80)
//						.setPressureMillibars((short)1100)
//						.setConditionText("Snowy")
//						.setWindSpeedMs(5.3)
//						.setCity("Minsk")
//						.setRequestedAt(Instant.parse("2023-11-29T03:00:00Z"))
//				);
//        };
//    }
}
