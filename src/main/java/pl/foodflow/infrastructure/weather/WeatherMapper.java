package pl.foodflow.infrastructure.weather;

import org.springframework.stereotype.Component;
import pl.foodflow.api.dto.WeatherDataDTO;

import java.util.Map;


@Component
public class WeatherMapper {

    public WeatherDataDTO map(Map<?, ?> location, Map<?, ?> current, Map<?, ?> condition) {
        return WeatherDataDTO.builder()
                .name(location.get("name").toString())
                .region(location.get("region").toString())
                .country(location.get("country").toString())
                .localtime(location.get("localtime").toString())
                .temp_c((Integer) current.get("temp_c"))
                .temp_f((double) current.get("temp_f"))
                .wind_mph((double) current.get("wind_mph"))
                .wind_kph((double) current.get("wind_kph"))
                .wind_dir(current.get("wind_dir").toString())
                .feelslike_c((double) current.get("feelslike_c"))
                .feelslike_f((double) current.get("feelslike_f"))
                .gust_kph((double) current.get("gust_kph"))
                .gust_mph((double) current.get("gust_mph"))
                .precip_mm((Integer) current.get("precip_mm"))
                .humidity((int) current.get("humidity"))
                .vis_km((Integer) current.get("vis_km"))
                .uv((Integer) current.get("uv"))
                .icon(condition.get("icon").toString())
                .text(condition.get("text").toString())
                .build();
    }
}
