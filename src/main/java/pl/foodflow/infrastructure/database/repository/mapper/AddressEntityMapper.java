package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.Address;
import pl.foodflow.infrastructure.database.entity.AddressEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AddressEntityMapper {

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "restaurantAddresses", ignore = true)
    AddressEntity mapToEntity(Address address);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "restaurant", ignore = true)

    @Mapping(target = "restaurantAddresses.address.owner", ignore = true)
    @Mapping(target = "restaurantAddresses.address.customer", ignore = true)
    @Mapping(target = "restaurantAddresses.address.restaurant", ignore = true)
    @Mapping(target = "restaurantAddresses.address.restaurantAddresses", ignore = true)

    @Mapping(target = "restaurantAddresses.restaurant.address", ignore = true)
    @Mapping(target = "restaurantAddresses.restaurant.owner", ignore = true)
    @Mapping(target = "restaurantAddresses.restaurant.menu", ignore = true)
    @Mapping(target = "restaurantAddresses.restaurant.restaurantAddresses", ignore = true)
    @Mapping(target = "restaurantAddresses.restaurant.restaurantCategories", ignore = true)
    @Mapping(target = "restaurantAddresses.restaurant.orderRecords", ignore = true)
    Address mapFromEntity(AddressEntity saved);

}
