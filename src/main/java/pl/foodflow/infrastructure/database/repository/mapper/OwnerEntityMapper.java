package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.*;
import pl.foodflow.infrastructure.database.entity.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OwnerEntityMapper {
    @Mapping(target = "restaurant", ignore = true)
    OwnerEntity mapToEntity(Owner owner);

    default Owner mapFromEntity(OwnerEntity entity) {
        if (entity == null) {
            return null;
        }

        return Owner.builder()
                .ownerId(entity.getOwnerId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .restaurant(mapRestaurant(entity.getRestaurant()))
                .build();
    }

    default Restaurant mapRestaurant(RestaurantEntity saved) {
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
                .price(categoryItemEntity.getPrice())
                .imageUrl(categoryItemEntity.getImageUrl())
                .build();
    }
}
