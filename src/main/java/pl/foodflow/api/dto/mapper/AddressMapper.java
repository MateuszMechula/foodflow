package pl.foodflow.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.foodflow.api.dto.AddressDTO;
import pl.foodflow.api.dto.AddressRequestDTO;
import pl.foodflow.domain.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "restaurantAddresses", ignore = true)
    Address map(AddressDTO dto);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "restaurantAddresses", ignore = true)
    @Mapping(target = "addressId", ignore = true)
    Address map(AddressRequestDTO dto);
}
