package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OwnerEntityMapper {
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    OwnerEntity mapToEntity(Owner owner);

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Owner mapFromEntity(OwnerEntity saved);
}
