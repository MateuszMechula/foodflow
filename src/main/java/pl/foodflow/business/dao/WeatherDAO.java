package pl.foodflow.business.dao;

import pl.foodflow.api.dto.WeatherDataDTO;

import java.util.Optional;

public interface WeatherDAO {

    Optional<WeatherDataDTO> getWeatherLocationParameter(String locationParameter, String lang);
}
