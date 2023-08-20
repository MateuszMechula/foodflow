package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {

    RestaurantEntity mapToEntity(Restaurant restaurant);

    Restaurant mapFromEntity(RestaurantEntity saved);
}
