package pl.foodflow.util;

import pl.foodflow.domain.*;
import pl.foodflow.enums.DeliveryType;
import pl.foodflow.enums.OrderStatus;
import pl.foodflow.infrastructure.database.entity.*;
import pl.foodflow.infrastructure.security.role.RoleEntity;
import pl.foodflow.infrastructure.security.user.User;
import pl.foodflow.infrastructure.security.user.UserEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Set;

public class TestDataForMappers {

    public static User someUser() {
        return User.builder()
                .userId(1)
                .userName("user1")
                .password("test")
                .active(true)
                .roles(Set.of(RoleEntity.builder().role("OWNER").build()))
                .build();
    }

    public static UserEntity someUserEntity() {
        return UserEntity.builder()
                .userId(1)
                .userName("testOwner")
                .password("test")
                .active(true)
                .roles(Set.of(RoleEntity.builder().role("OWNER").build()))
                .build();
    }

    public static Owner someOwner() {
        return Owner.builder()
                .ownerId(1L)
                .name("Patryk")
                .surname("Nowobogacki")
                .email("patryknowobogacki@gmail.com")
                .phone("604555005")
                .address(someAddress())
                .userId(someUser().getUserId())
                .build();
    }

    public static OwnerEntity someOwnerEntity() {
        return OwnerEntity.builder()
                .ownerId(1L)
                .name("Patryk")
                .surname("Nowobogacki")
                .email("patryknowobogacki@gmail.com")
                .phone("604555005")
                .address(AddressEntity.builder()
                        .street("Słowackiego")
                        .city("Gdańsk")
                        .postalCode("80-130")
                        .country("Polska")
                        .restaurantAddresses(Set.of(RestaurantAddressEntity.builder()
                                .restaurant(RestaurantEntity.builder().build())
                                .build()))
                        .build())
                .userId(someUser().getUserId())
                .restaurant(RestaurantEntity.builder().restaurantId(1L).build())
                .build();
    }

    public static Customer someCustomer() {
        return Customer.builder()
                .customerId(1L)
                .name("Jan")
                .surname("Kowalski")
                .email("jankowalski@gmial.com")
                .address(Address.builder()
                        .city("Gdańsk")
                        .country("Polska")
                        .postalCode("80-180")
                        .street("Niepołomicka")
                        .build())
                .phone("505440550")
                .address(Address.builder()
                        .owner(Owner.builder()
                                .address(Address.builder().build())
                                .restaurant(Restaurant.builder()
                                        .restaurantAddresses(Set.of(RestaurantAddress.builder().build()))
                                        .restaurantCategories(Set.of(RestaurantCategory.builder()
                                                .category(Category.builder().build())
                                                .build()))
                                        .orderRecords(Set.of(OrderRecord.builder().build()))
                                        .build())
                                .build())
                        .customer(Customer.builder()
                                .address(Address.builder().build())
                                .orderRecords(Set.of(OrderRecord.builder()
                                        .orderItems(Set.of(OrderItem.builder()
                                                .categoryItem(CategoryItem.builder()
                                                        .menuCategory(MenuCategory.builder()
                                                                .menu(Menu.builder().build())
                                                                .build())
                                                        .build())
                                                .build()))
                                        .build()))
                                .build())
                        .restaurant(someRestaurant())
                        .restaurantAddresses(Set.of(RestaurantAddress.builder().build()))
                        .build())
                .userId(someUser().getUserId())
                .build();
    }

    public static CustomerEntity someCustomerEntity() {
        return CustomerEntity.builder()
                .customerId(1L)
                .name("Jan")
                .surname("Kowalski")
                .email("jankowalski@gmial.com")
                .address(AddressEntity.builder()
                        .city("Gdańsk")
                        .country("Polska")
                        .postalCode("80-180")
                        .street("Niepołomicka")
                        .build())
                .phone("505440550")
                .userId(someUser().getUserId())
                .build();
    }

    public static Address someAddress() {
        return Address.builder()
                .street("Niepołomicka 66")
                .postalCode("77-555")
                .city("City")
                .country("Polska")
                .owner(Owner.builder().build())
                .customer(someCustomer())
                .restaurant(someRestaurant())
                .restaurantAddresses(Set.of(someRestaurantAddress()))
                .build();
    }

    public static AddressEntity someAddressEntity() {
        return AddressEntity.builder()
                .addressId(1L)
                .street("Wolska 23")
                .postalCode("11-999")
                .city("Węgorzewo")
                .country("Polska")
                .restaurantAddresses(Set.of(RestaurantAddressEntity.builder()
                        .restaurant(someRestaurantEntity())
                        .address(AddressEntity.builder()
                                .addressId(1L)
                                .build())
                        .build()))
                .build();
    }

    public static Restaurant someRestaurant() {
        return Restaurant.builder()
                .restaurantId(1L)
                .nip("7244505040")
                .name("Gospoda")
                .description("Najlepsza restauracja w trójmieście")
                .openTime(OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC))
                .closeTime(OffsetTime.of(22, 30, 0, 0, ZoneOffset.UTC))
                .phone("504005669")
                .minimumOrderAmount(BigDecimal.valueOf(30))
                .deliveryPrice(BigDecimal.valueOf(6))
                .deliveryOption(true)
                .address(Address.builder()
                        .street("Orzechowa")
                        .postalCode("80-300")
                        .city("Gdańsk")
                        .country("Polska")
                        .build())
                .menu(Menu.builder()
                        .menuCategories(Set.of(MenuCategory.builder()
                                .categoryItems(Set.of(CategoryItem.builder()
                                        .menuCategory(MenuCategory.builder().build())
                                        .orderItems(Set.of(OrderItem.builder()
                                                .orderRecord(OrderRecord.builder()
                                                        .customer(Customer.builder().build())
                                                        .build())
                                                .build()))
                                        .build()))
                                .build()))
                        .build())
                .restaurantAddresses(Set.of(RestaurantAddress.builder()
                        .address(Address.builder().build())
                        .build()))
                .restaurantCategories(Set.of(RestaurantCategory.builder()
                        .category(Category.builder().build())
                        .build()))
                .orderRecords(Set.of(OrderRecord.builder()
                        .orderItems(Set.of(OrderItem.builder()
                                .categoryItem(CategoryItem.builder()
                                        .menuCategory(MenuCategory.builder()
                                                .build())
                                        .build())
                                .build()))
                        .customer(Customer.builder()
                                .orderRecords(Set.of(OrderRecord.builder().build()))
                                .build())
                        .build()))
                .owner(Owner.builder().build())
                .build();
    }

    public static RestaurantEntity someRestaurantEntity() {
        return RestaurantEntity.builder()
                .restaurantId(1L)
                .nip("7244505040")
                .name("Gospoda")
                .description("Najlepsza restauracja w trójmieście")
                .openTime(OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC))
                .closeTime(OffsetTime.of(22, 30, 0, 0, ZoneOffset.UTC))
                .phone("504005669")
                .minimumOrderAmount(BigDecimal.valueOf(30))
                .deliveryPrice(BigDecimal.valueOf(6))
                .deliveryOption(true)
                .address(AddressEntity.builder()
                        .street("Orzechowa")
                        .postalCode("80-300")
                        .city("Gdańsk")
                        .country("Polska")
                        .build())
                .menu(MenuEntity.builder()
                        .menuCategories(Set.of(MenuCategoryEntity.builder()
                                .categoryItems(Set.of(CategoryItemEntity.builder()
                                        .menuCategory(MenuCategoryEntity.builder().build())
                                        .orderItems(Set.of(OrderItemEntity.builder()
                                                .orderRecord(OrderRecordEntity.builder()
                                                        .customer(CustomerEntity.builder().build())
                                                        .build())
                                                .build()))
                                        .build()))
                                .build()))
                        .build())
                .restaurantAddresses(Set.of(RestaurantAddressEntity.builder()
                        .address(AddressEntity.builder().build())
                        .build()))
                .restaurantCategories(Set.of(RestaurantCategoryEntity.builder()
                        .category(CategoryEntity.builder().build())
                        .build()))
                .orderRecords(Set.of(OrderRecordEntity.builder()
                        .orderItems(Set.of(OrderItemEntity.builder()
                                .categoryItem(CategoryItemEntity.builder()
                                        .menuCategory(MenuCategoryEntity.builder()
                                                .build())
                                        .build())
                                .build()))
                        .customer(CustomerEntity.builder().build())
                        .build()))
                .owner(OwnerEntity.builder().build())
                .restaurantAddresses(Set.of(RestaurantAddressEntity.builder()
                        .restaurant(RestaurantEntity.builder().build())
                        .address(AddressEntity.builder().build())
                        .build()))
                .build();
    }

    public static RestaurantAddress someRestaurantAddress() {
        return RestaurantAddress.builder()
                .restaurantAddressId(1L)
                .restaurant(someRestaurant())
                .address(Address.builder()
                        .addressId(1L)
                        .street("klonowa")
                        .postalCode("11-500")
                        .city("Giżycko")
                        .country("Polska")
                        .customer(Customer.builder().build())
                        .restaurant(someRestaurant())
                        .restaurantAddresses(Set.of(RestaurantAddress.builder().build()))
                        .build())
                .build();
    }

    public static RestaurantAddressEntity someRestaurantAddressEntity() {
        return RestaurantAddressEntity.builder()
                .restaurantAddressId(1L)
                .restaurant(someRestaurantEntity())
                .address(AddressEntity.builder()
                        .addressId(1L)
                        .street("klonowa")
                        .postalCode("11-500")
                        .city("Giżycko")
                        .country("Polska")
                        .build())
                .build();
    }

    public static Menu someMenu() {
        return Menu.builder()
                .menuId(1L)
                .name("Moje menu")
                .description("Zapraszamy")
                .restaurant(Restaurant.builder()
                        .restaurantId(1L)
                        .owner(Owner.builder().ownerId(1L).build())
                        .restaurantAddresses(Set.of(RestaurantAddress.builder().restaurantAddressId(1L).build()))
                        .restaurantCategories(Set.of(RestaurantCategory.builder().restaurantCategoryId(1L).build()))
                        .orderRecords(Set.of(OrderRecord.builder()
                                .orderRecordId(1L)
                                .orderItems(Set.of(OrderItem.builder().orderItemId(1L).build()))
                                .customer(Customer.builder().build())
                                .build()))
                        .address(Address.builder().build())
                        .build())
                .menuCategories(Set.of(MenuCategory.builder()
                        .categoryItems(Set.of(CategoryItem.builder().build()))
                        .build()))
                .build();
    }

    public static MenuEntity someMenuEntity() {
        return MenuEntity.builder()
                .menuId(1L)
                .name("Moje menu")
                .description("Zapraszamy")
                .restaurant(RestaurantEntity.builder()
                        .restaurantId(1L)
                        .owner(OwnerEntity.builder().ownerId(1L).build())
                        .restaurantAddresses(Set.of(RestaurantAddressEntity.builder().restaurantAddressId(1L).build()))
                        .restaurantCategories(Set.of(RestaurantCategoryEntity.builder().restaurantCategoryId(1L).build()))
                        .orderRecords(Set.of(OrderRecordEntity.builder()
                                .orderRecordId(1L)
                                .orderItems(Set.of(OrderItemEntity.builder().orderItemId(1L).build()))
                                .build()))
                        .build())
                .menuCategories(Set.of(MenuCategoryEntity.builder().menuCategoryId(1L).build()))
                .build();
    }

    public static MenuCategory someMenuCategory() {
        return MenuCategory.builder()
                .menuCategoryId(1L)
                .name("Burgery")
                .description("Mega dobre burgerki")
                .categoryItems(Set.of(someCategoryItem()))
                .menu(Menu.builder()
                        .menuId(1L)
                        .restaurant(Restaurant.builder()
                                .restaurantId(1L)
                                .address(Address.builder()
                                        .customer(Customer.builder().build())
                                        .build())
                                .owner(Owner.builder()
                                        .address(Address.builder().build())
                                        .restaurant(Restaurant.builder()
                                                .restaurantCategories(Set.of(RestaurantCategory.builder()
                                                        .category(Category.builder().build())
                                                        .build()))
                                                .build())
                                        .build())
                                .restaurantAddresses(Set.of(RestaurantAddress.builder().build()))
                                .restaurantCategories(Set.of(RestaurantCategory.builder().build()))
                                .orderRecords(Set.of(OrderRecord.builder()
                                        .orderItems(Set.of(OrderItem.builder().build()))
                                        .build()))
                                .build())
                        .menuCategories(Set.of(MenuCategory.builder().menuCategoryId(1L).build()))
                        .build())
                .build();
    }

    public static MenuCategoryEntity someMenuCategoryEntity() {
        return MenuCategoryEntity.builder()
                .menuCategoryId(1L)
                .name("Burgery")
                .description("Mega dobre burgerki")
                .categoryItems(Set.of(CategoryItemEntity.builder().build()))
                .build();
    }

    public static CategoryItem someCategoryItem() {
        return CategoryItem.builder()
                .categoryItemId(1L)
                .name("McChicken")
                .description("Soczysty kurczak")
                .price(BigDecimal.valueOf(12))
                .imageUrl("3402okc02-8dc3-4de1-acd2-134221040_")
                .orderItems(Set.of(OrderItem.builder()
                        .categoryItem(CategoryItem.builder()
                                .menuCategory(MenuCategory.builder().build())
                                .orderItems(Set.of(OrderItem.builder().build()))
                                .build())
                        .orderRecord(
                                OrderRecord.builder()
                                        .restaurant(someRestaurant())
                                        .customer(someCustomer())
                                        .orderItems(Set.of(someOrderItem()))
                                        .build())
                        .build()))
                .menuCategory(MenuCategory.builder()
                        .menu(Menu.builder()
                                .restaurant(Restaurant.builder().build())
                                .menuCategories(Set.of(MenuCategory.builder().build()))
                                .build())
                        .build())
                .build();
    }

    public static CategoryItemEntity someCategoryItemEntity() {
        return CategoryItemEntity.builder()
                .categoryItemId(1L)
                .name("McChicken")
                .description("Soczysty kurczak")
                .price(BigDecimal.valueOf(12))
                .imageUrl("3402okc02-8dc3-4de1-acd2-134221040_")
                .orderItems(Set.of(OrderItemEntity.builder()
                        .categoryItem(CategoryItemEntity.builder()
                                .menuCategory(MenuCategoryEntity.builder().build())
                                .orderItems(Set.of(OrderItemEntity.builder().build()))
                                .build())
                        .orderRecord(
                                OrderRecordEntity.builder()
                                        .restaurant(RestaurantEntity.builder()
                                                .owner(OwnerEntity.builder().build())
                                                .restaurantCategories(Set.of(RestaurantCategoryEntity.builder().build()))
                                                .restaurantAddresses(Set.of(RestaurantAddressEntity.builder().build()))
                                                .build())
                                        .customer(CustomerEntity.builder()
                                                .address(AddressEntity.builder().build())
                                                .build())
                                        .orderItems(Set.of(
                                                OrderItemEntity.builder()
                                                        .categoryItem(CategoryItemEntity.builder().build())
                                                        .orderRecord(OrderRecordEntity.builder().build())
                                                        .build()))
                                        .build())
                        .build()))
                .menuCategory(MenuCategoryEntity.builder()
                        .menu(MenuEntity.builder()
                                .restaurant(RestaurantEntity.builder().build())
                                .menuCategories(Set.of(MenuCategoryEntity.builder().build()))
                                .build())
                        .build())
                .build();
    }

    public static OrderItem someOrderItem() {
        return OrderItem.builder()
                .orderItemId(1L)
                .unitPrice(BigDecimal.valueOf(20))
                .quantity(5)
                .orderRecord(OrderRecord.builder()
                        .restaurant(Restaurant.builder()
                                .owner(Owner.builder().build())
                                .restaurantAddresses(Set.of(RestaurantAddress.builder()
                                        .address(Address.builder().build())
                                        .build()))
                                .restaurantCategories(Set.of(RestaurantCategory.builder()
                                        .category(Category.builder().build())
                                        .build()))
                                .menu(Menu.builder()
                                        .menuCategories(Set.of(MenuCategory.builder()
                                                .categoryItems(Set.of(CategoryItem.builder().build()))
                                                .build()))
                                        .build())
                                .build())
                        .orderItems(Set.of(OrderItem.builder().build()))
                        .customer(Customer.builder().build())
                        .build())
                .categoryItem(CategoryItem.builder()
                        .orderItems(Set.of(OrderItem.builder()
                                .orderRecord(OrderRecord.builder().build())
                                .build()))
                        .menuCategory(MenuCategory.builder()
                                .menu(Menu.builder()
                                        .restaurant(Restaurant.builder().build())
                                        .build())
                                .build())
                        .build())
                .build();
    }

    public static OrderItemEntity someOrderItemEntity() {
        return OrderItemEntity.builder()
                .orderItemId(2L)
                .unitPrice(BigDecimal.valueOf(30))
                .quantity(5)
                .orderRecord(OrderRecordEntity.builder().build())
                .build();
    }

    public static OrderRecord someOrderRecord() {
        return OrderRecord.builder()
                .orderRecordId(1L)
                .orderNumber("ORDER15")
                .orderDateTime(OffsetDateTime.of(2020, 5, 5, 0, 0, 0, 0, ZoneOffset.UTC))
                .orderStatus(OrderStatus.COMPLETED.toString())
                .orderNotes("proszę o szybką dostawę")
                .totalAmount(BigDecimal.valueOf(150))
                .contactPhone("505440003")
                .deliveryAddress("Różana 15/22, 11-500 Tczew")
                .deliveryType(DeliveryType.DELIVERY.toString())
                .orderItems(Set.of(OrderItem.builder().build()))
                .build();
    }

    public static OrderRecordEntity someOrderRecordEntity() {
        return OrderRecordEntity.builder()
                .orderRecordId(1L)
                .orderNumber("ORDER15")
                .orderDateTime(OffsetDateTime.of(2020, 5, 5, 0, 0, 0, 0, ZoneOffset.UTC))
                .orderStatus(OrderStatus.COMPLETED.toString())
                .orderNotes("proszę o szybką dostawę")
                .totalAmount(BigDecimal.valueOf(150))
                .contactPhone("505440003")
                .deliveryAddress("Różana 15/22, 11-500 Tczew")
                .deliveryType(DeliveryType.DELIVERY.toString())
                .orderItems(Set.of(OrderItemEntity.builder().build()))
                .build();
    }
}
