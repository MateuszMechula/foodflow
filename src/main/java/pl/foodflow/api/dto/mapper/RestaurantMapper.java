package pl.foodflow.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.domain.Restaurant;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    @Mapping(target = "restaurantId", ignore = true)
    Restaurant mapToDTO(RestaurantDTO restaurantDTO);
}
