package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.Address;
import pl.foodflow.infrastructure.database.entity.AddressEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AddressEntityMapper {

    AddressEntity mapToEntity(Address address);

    Address mapFromEntity(AddressEntity saved);

}
