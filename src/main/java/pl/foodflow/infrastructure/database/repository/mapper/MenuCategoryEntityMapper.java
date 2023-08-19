package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.infrastructure.database.entity.MenuCategoryEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MenuCategoryEntityMapper {
    MenuCategoryEntity mapToEntity(MenuCategory menuCategory);

    MenuCategory mapFromEntity(MenuCategoryEntity entity);
}
