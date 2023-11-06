package pl.foodflow.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.api.dto.RestaurantRequestDTO;
import pl.foodflow.domain.Restaurant;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Mapper(uses = AddressMapper.class, componentModel = "spring")
public interface RestaurantMapper {
    @Mapping(target = "restaurantId", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "menu", ignore = true)
    @Mapping(target = "restaurantAddresses", ignore = true)
    @Mapping(target = "restaurantCategories", ignore = true)
    @Mapping(target = "orderRecords", ignore = true)
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

    @Named("mapOffsetTimeToString")
    default String mapOffsetTimeToString(OffsetTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.format(formatter);
    }

    @Mapping(source = "owner.ownerId", target = "ownerId")
    @Mapping(source = "owner.email", target = "ownerEmail")
    @Mapping(source = "menu.menuId", target = "menuId")
    @Mapping(source = "openTime", target = "openTime", qualifiedByName = "mapOffsetTimeToString")
    @Mapping(source = "closeTime", target = "closeTime", qualifiedByName = "mapOffsetTimeToString")
    RestaurantDTO mapToDTO(Restaurant restaurant);

    @Mapping(source = "openTime", target = "openTime", qualifiedByName = "mapStringToOffsetTime")
    @Mapping(source = "closeTime", target = "closeTime", qualifiedByName = "mapStringToOffsetTime")
    @Mapping(target = "restaurantId", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "menu", ignore = true)
    @Mapping(target = "restaurantAddresses", ignore = true)
    @Mapping(target = "restaurantCategories", ignore = true)
    @Mapping(target = "orderRecords", ignore = true)
    Restaurant mapRequest(RestaurantRequestDTO requestDTO);
}
