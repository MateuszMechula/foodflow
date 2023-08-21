package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.database.entity.CategoryItemEntity;
import pl.foodflow.infrastructure.database.entity.MenuCategoryEntity;
import pl.foodflow.infrastructure.database.entity.MenuEntity;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {

    RestaurantEntity mapToEntity(Restaurant restaurant);

//    @Mapping(target = "ownerEmail", ignore = true)
//    @Mapping(target = "address", ignore = true)
//    @Mapping(target = "owner", ignore = true)
//    Restaurant mapFromEntity(RestaurantEntity saved);

    default Restaurant mapFromEntity(RestaurantEntity saved) {
        if (saved == null) {
            return null;
        }

        return Restaurant.builder()
                .restaurantId(saved.getRestaurantId())
                .nip(saved.getNip())
                .name(saved.getName())
                .description(saved.getDescription())
                .openTime(saved.getOpenTime())
                .closeTime(saved.getCloseTime())
                .phone(saved.getPhone())
                .minimumOrderAmount(saved.getMinimumOrderAmount())
                .deliveryPrice(saved.getDeliveryPrice())
                .deliveryOption(saved.getDeliveryOption())
                .menu(mapMenu(saved.getMenu()))
                .build();
    }

    default Menu mapMenu(MenuEntity menuEntity) {
        if (menuEntity == null) {
            return null;
        }
        return Menu.builder()
                .menuId(menuEntity.getMenuId())
                .name(menuEntity.getName())
                .description(menuEntity.getDescription())
                .menuCategories(mapMenuCategories(menuEntity.getMenuCategories()))
                .build();

    }

    default Set<MenuCategory> mapMenuCategories(Set<MenuCategoryEntity> menuCategoryEntities) {
        if (menuCategoryEntities == null) {
            return null;
        }

        return menuCategoryEntities.stream()
                .map(this::mapMenuCategory)
                .collect(Collectors.toSet()
                );
    }

    default MenuCategory mapMenuCategory(MenuCategoryEntity menuCategoryEntity) {
        if (menuCategoryEntity == null) {
            return null;
        }

        return MenuCategory.builder()
                .menuCategoryId(menuCategoryEntity.getMenuCategoryId())
                .name(menuCategoryEntity.getName())
                .description(menuCategoryEntity.getDescription())
                .categoryItems(mapCategoryItems(menuCategoryEntity.getCategoryItems()))
                .build();
    }

    default Set<CategoryItem> mapCategoryItems(Set<CategoryItemEntity> categoryItemEntities) {
        if (categoryItemEntities == null) {
            return null;
        }

        return categoryItemEntities.stream()
                .map(this::mapCategoryItem)
                .collect(Collectors.toSet());
    }

    default CategoryItem mapCategoryItem(CategoryItemEntity categoryItemEntity) {
        if (categoryItemEntity == null) {
            return null;
        }

        return CategoryItem.builder()
                .categoryItemId(categoryItemEntity.getCategoryItemId())
                .name(categoryItemEntity.getName())
                .description(categoryItemEntity.getDescription())
                .build();
    }
}
