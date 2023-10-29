package pl.foodflow.infrastructure.weather;

import org.springframework.stereotype.Component;
import pl.foodflow.api.dto.WeatherDataDTO;

import java.util.Map;


@Component
public class WeatherMapper {

    public WeatherDataDTO map(Map<?, ?> location, Map<?, ?> current, Map<?, ?> condition) {
        WeatherDataDTO.WeatherDataDTOBuilder builder = WeatherDataDTO.builder()
                .name(getString(location, "name"))
                .region(getString(location, "region"))
                .country(getString(location, "country"))
                .localtime(getString(location, "localtime"))
                .wind_dir(getString(current, "wind_dir"))
                .icon(getString(condition, "icon"))
                .text(getString(condition, "text"));

        Integer tempCInt = getInteger(current, "temp_c");
        Double tempCDouble = getDouble(current, "temp_c");

        if (tempCInt != null) {
            builder.temp_c(tempCInt);
        } else if (tempCDouble != null) {
            builder.temp_c((int) Math.round(tempCDouble));
        }

        builder.temp_f(getDouble(current, "temp_f"))
                .wind_mph(getDouble(current, "wind_mph"))
                .wind_kph(getDouble(current, "wind_kph"))
                .feelslike_c(getDouble(current, "feelslike_c"))
                .feelslike_f(getDouble(current, "feelslike_f"))
                .gust_kph(getDouble(current, "gust_kph"))
                .gust_mph(getDouble(current, "gust_mph"))
                .humidity(getInteger(current, "humidity"));

        return builder.build();
    }

    private String getString(Map<?, ?> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    private Integer getInteger(Map<?, ?> map, String key) {
        Object value = map.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Double) {
            return (int) Math.round((Double) value);
        }
        return null;
    }

    private Double getDouble(Map<?, ?> map, String key) {
        Object value = map.get(key);
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Integer) {
            return (double) (Integer) value;
        }
        return null;
    }
}
