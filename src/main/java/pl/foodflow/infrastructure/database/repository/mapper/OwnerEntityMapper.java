package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OwnerEntityMapper {
    @Mappings({@Mapping(target = "address", ignore = true)})
    OwnerEntity mapToEntity(Owner owner);

    @Mappings({@Mapping(target = "address", ignore = true)})
    Owner mapFromEntity(OwnerEntity saved);
}
