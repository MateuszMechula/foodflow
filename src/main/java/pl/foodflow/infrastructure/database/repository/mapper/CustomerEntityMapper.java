package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.Customer;
import pl.foodflow.infrastructure.database.entity.CustomerEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "orderRecords", ignore = true)
    Customer mapFromEntity(CustomerEntity entity);
}
