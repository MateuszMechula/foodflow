package pl.foodflow.infrastructure.weather;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.foodflow.api.dto.WeatherDataDTO;
import pl.foodflow.business.dao.WeatherDAO;
import pl.foodflow.infrastructure.weather.api.ApisApi;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class WeatherClientImpl implements WeatherDAO {
    private final ApisApi apisApi;
    private final WeatherMapper weatherMapper;

    @Override
    public Optional<WeatherDataDTO> getWeatherLocationParameter(String locationParameter, String lang) {
        Object obj = fetchWeatherData(locationParameter, lang);

        if (obj instanceof Map<?, ?> responseMap) {

            Map<?, ?> location = (Map<?, ?>) responseMap.get("location");
            Map<?, ?> current = (Map<?, ?>) responseMap.get("current");
            Map<?, ?> condition = (Map<?, ?>) current.get("condition");

            WeatherDataDTO weatherDataDTO = weatherMapper.map(location, current, condition);
            return Optional.ofNullable(weatherDataDTO);
        }
        return Optional.empty();
    }

    private Object fetchWeatherData(String locationParameter, String lang) {
        return apisApi.realtimeWeather(locationParameter, lang).block();
    }
}
