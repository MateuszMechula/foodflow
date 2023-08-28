package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.Address;
import pl.foodflow.infrastructure.database.entity.AddressEntity;

@Mapper(uses = RestaurantEntityMapper.class, componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AddressEntityMapper {

    AddressEntity mapToEntity(Address address);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "restaurantAddresses.address.owner", ignore = true)
    @Mapping(target = "restaurantAddresses.address.customer", ignore = true)
    @Mapping(target = "restaurantAddresses.address.restaurant", ignore = true)
    Address mapFromEntity(AddressEntity saved);

}
