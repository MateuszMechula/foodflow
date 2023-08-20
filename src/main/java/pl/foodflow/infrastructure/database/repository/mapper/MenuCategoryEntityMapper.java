package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.infrastructure.database.entity.MenuCategoryEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MenuCategoryEntityMapper {
    MenuCategoryEntity mapToEntity(MenuCategory menuCategory);

    @Mapping(target = "menu", ignore = true)
    @Mapping(target = "categoryItems", ignore = true)
    MenuCategory mapFromEntity(MenuCategoryEntity entity);
}
