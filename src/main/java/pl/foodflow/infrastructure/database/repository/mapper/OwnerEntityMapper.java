package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;

@Mapper(uses = RestaurantEntityMapper.class, componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OwnerEntityMapper {
    @Mapping(target = "restaurant", ignore = true)
    OwnerEntity mapToEntity(Owner owner);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "address", ignore = true)
    Owner mapFromEntity(OwnerEntity entity);

}
