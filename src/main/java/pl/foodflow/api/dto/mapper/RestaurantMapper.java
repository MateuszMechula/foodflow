package pl.foodflow.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.domain.Restaurant;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    @Mapping(target = "restaurantId", ignore = true)
    @Mapping(source = "openTime", target = "openTime", qualifiedByName = "mapStringToOffsetTime")
    @Mapping(source = "closeTime", target = "closeTime", qualifiedByName = "mapStringToOffsetTime")
    Restaurant map(RestaurantDTO restaurantDTO);

    @Named("mapStringToOffsetTime")
    default OffsetTime mapStringToOffsetTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(timeString, formatter);
        ZoneOffset offset = ZoneOffset.UTC;
        return OffsetTime.of(localTime, offset);
    }
}
