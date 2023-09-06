package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.infrastructure.database.entity.MenuCategoryEntity;

@Mapper(uses = CategoryItemEntityMapper.class,
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MenuCategoryEntityMapper {

    @Mapping(target = "categoryItems", ignore = true)
    MenuCategoryEntity mapToEntity(MenuCategory menuCategory);

    @Mapping(target = "menu.restaurant", ignore = true)
    @Mapping(target = "menu.menuCategories", ignore = true)
    MenuCategory mapFromEntity(MenuCategoryEntity entity);
}
