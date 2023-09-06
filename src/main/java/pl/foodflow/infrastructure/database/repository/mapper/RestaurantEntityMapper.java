package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;

@Mapper(uses = {MenuEntityMapper.class, RestaurantAddressMapper.class},
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {

    RestaurantEntity mapToEntity(Restaurant restaurant);

    @Mapping(target = "owner.address", ignore = true)
    @Mapping(target = "owner.restaurant", ignore = true)
    @Mapping(target = "restaurantCategories", ignore = true)
    @Mapping(target = "orderRecords", ignore = true)

    @Mapping(target = "address.owner", ignore = true)
    @Mapping(target = "address.customer", ignore = true)
    @Mapping(target = "address.restaurant", ignore = true)
    @Mapping(target = "address.restaurantAddresses", ignore = true)
    Restaurant mapFromEntity(RestaurantEntity entity);

}
