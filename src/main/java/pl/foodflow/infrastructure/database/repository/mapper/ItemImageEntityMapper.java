package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.ItemImage;
import pl.foodflow.infrastructure.database.entity.ItemImageEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ItemImageEntityMapper {
    ItemImageEntity mapToEntity(ItemImage itemImage);

    ItemImage mapFromEntity(ItemImageEntity entity);
}
