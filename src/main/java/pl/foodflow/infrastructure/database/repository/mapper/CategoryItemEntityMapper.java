package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.infrastructure.database.entity.CategoryItemEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryItemEntityMapper {

    @Mapping(target = "orderItems.categoryItem", ignore = true)
    @Mapping(target = "orderItems.orderItem", ignore = true)
    @Mapping(target = "menuCategory.categoryItems", ignore = true)
    @Mapping(target = "menuCategory.menu.restaurant", ignore = true)
    @Mapping(target = "menuCategory.menu.menuCategories", ignore = true)
    CategoryItemEntity mapToEntity(CategoryItem entity);

    @Mapping(target = "menuCategory.menu", ignore = true)
    @Mapping(target = "menuCategory.categoryItems", ignore = true)
    @Mapping(target = "orderItems.categoryItem", ignore = true)
    @Mapping(target = "orderItems.orderRecord", ignore = true)
    CategoryItem mapFromEntity(CategoryItemEntity entity);
}
