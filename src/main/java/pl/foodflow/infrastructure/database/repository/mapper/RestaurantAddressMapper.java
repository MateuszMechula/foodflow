package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.RestaurantAddress;
import pl.foodflow.infrastructure.database.entity.RestaurantAddressEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantAddressMapper {

    RestaurantAddressEntity mapToEntity(RestaurantAddress address);

    RestaurantAddress mapFromEntity(RestaurantAddressEntity saved);
}
