package io.weather.app.mapper;

import io.weather.app.dto.weather.WeatherInfoDto;
import io.weather.app.entity.WeatherInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeatherInfoMapper {
    WeatherInfoMapper INSTANCE = Mappers.getMapper(WeatherInfoMapper.class);

    WeatherInfoDto toDto(WeatherInfoEntity entity);
    WeatherInfoEntity toEntity(WeatherInfoDto dto);
}
