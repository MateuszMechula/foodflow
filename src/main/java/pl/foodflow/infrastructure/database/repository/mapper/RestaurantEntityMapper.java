package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.*;
import pl.foodflow.infrastructure.database.entity.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {

    RestaurantEntity mapToEntity(Restaurant restaurant);

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
                .restaurantAddresses(mapRestaurantAddresses(saved.getRestaurantAddresses()))
                .address(mapRestaurantAddress(saved))
                .build();
    }

    default Address mapRestaurantAddress(RestaurantEntity saved) {
        return Address.builder()
                .addressId(saved.getAddress().getAddressId())
                .street(saved.getAddress().getStreet())
                .city(saved.getAddress().getCity())
                .postalCode(saved.getAddress().getPostalCode())
                .country(saved.getAddress().getCountry())
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
                        .city(restaurantAddressEntity.getAddress().getCity())
                        .postalCode(restaurantAddressEntity.getAddress().getPostalCode())
                        .country(restaurantAddressEntity.getAddress().getCountry())
                        .build())
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
