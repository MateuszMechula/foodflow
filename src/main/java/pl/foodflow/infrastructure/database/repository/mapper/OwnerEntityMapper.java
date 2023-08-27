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
                .address(mapRestaurantAddresses(saved))
                .restaurantAddresses(mapRestaurantAddresses(saved.getRestaurantAddresses()))
                .build();
    }

    default Set<RestaurantAddress> mapRestaurantAddresses(Set<RestaurantAddressEntity> restaurantAddressesEntities) {
        if (restaurantAddressesEntities == null) {
            return null;
        }

        return restaurantAddressesEntities.stream()
                .map(this::mapRestaurantAddress)
                .collect(Collectors.toSet());


    }

    default RestaurantAddress mapRestaurantAddress(RestaurantAddressEntity restaurantAddressEntity) {
        if (restaurantAddressEntity == null) {
            return null;
        }

        return RestaurantAddress.builder()
                .restaurantAddressId(restaurantAddressEntity.getRestaurantAddressId())
                .restaurant(Restaurant.builder()
                        .restaurantId(restaurantAddressEntity.getRestaurant().getRestaurantId())
                        .build())
                .address(Address.builder()
                        .addressId(restaurantAddressEntity.getAddress().getAddressId())
                        .street(restaurantAddressEntity.getAddress().getStreet())
                        .postalCode(restaurantAddressEntity.getAddress().getPostalCode())
                        .city(restaurantAddressEntity.getAddress().getCity())
                        .country(restaurantAddressEntity.getAddress().getCountry())
                        .build())
                .build();
    }

    private static Address mapRestaurantAddresses(RestaurantEntity saved) {
        return Address.builder()
                .addressId(saved.getAddress().getAddressId())
                .street(saved.getAddress().getStreet())
                .city(saved.getAddress().getCity())
                .postalCode(saved.getAddress().getPostalCode())
                .country(saved.getAddress().getCountry())
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
                .restaurant(Restaurant.builder().restaurantId(menuEntity.getMenuId()).build())
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
