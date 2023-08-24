package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.infrastructure.database.entity.CategoryItemEntity;
import pl.foodflow.infrastructure.database.entity.MenuCategoryEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryItemEntityMapper {

    @Mapping(target = "orderItems", ignore = true)
    CategoryItem mapFromEntity(CategoryItemEntity entity);

//    @Mapping(target = "menuCategory", ignore = true)
//    CategoryItemEntity mapToEntity(CategoryItem categoryItem);


    default CategoryItemEntity mapToEntity(CategoryItem categoryItem) {
        return CategoryItemEntity.builder()
                .categoryItemId(categoryItem.getCategoryItemId())
                .name(categoryItem.getName())
                .description(categoryItem.getDescription())
                .price(categoryItem.getPrice())
                .imageUrl(categoryItem.getImageUrl())
                .menuCategory(mapMenuCategory(categoryItem.getMenuCategory()))
                .build();
    }

    default MenuCategoryEntity mapMenuCategory(MenuCategory menuCategory) {
        return MenuCategoryEntity.builder()
                .menuCategoryId(menuCategory.getMenuCategoryId())
                .name(menuCategory.getName())
                .description(menuCategory.getDescription())
                .build();
    }

    ;
}
