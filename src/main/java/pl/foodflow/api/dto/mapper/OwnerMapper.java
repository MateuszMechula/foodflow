package pl.foodflow.api.dto.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.foodflow.api.dto.OwnerDTO;
import pl.foodflow.domain.Owner;

@Mapper(uses = AddressMapper.class,componentModel = "spring")
public interface OwnerMapper {

    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Owner map(OwnerDTO dto);


}
