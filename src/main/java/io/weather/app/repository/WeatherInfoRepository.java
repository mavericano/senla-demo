package io.weather.app.repository;

import io.weather.app.entity.WeatherInfoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherInfoRepository extends CrudRepository<WeatherInfoEntity, Long> {
    WeatherInfoEntity findFirstByOrderByRequestedAtDesc();
}
