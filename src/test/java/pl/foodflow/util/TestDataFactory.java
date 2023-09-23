package pl.foodflow.util;

import pl.foodflow.enums.DeliveryType;
import pl.foodflow.enums.OrderStatus;
import pl.foodflow.infrastructure.database.entity.*;
import pl.foodflow.infrastructure.security.user.UserEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class TestDataFactory {

    public static UserEntity someUser1() {
        return UserEntity.builder()
                .userId(1)
                .userName("user1")
                .password("test")
                .active(true)
                .build();
    }

    public static UserEntity someUser2() {
        return UserEntity.builder()
                .userId(2)
                .userName("user2")
                .password("test")
                .active(true)
                .build();
    }

    public static UserEntity someUser3() {
        return UserEntity.builder()
                .userId(3)
                .userName("user3")
                .password("test")
                .active(true)
                .build();
    }

    public static UserEntity someUser4() {
        return UserEntity.builder()
                .userId(4)
                .userName("user3")
                .password("test")
                .active(true)
                .build();
    }

    public static CustomerEntity someCustomer1() {
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
                .userId(someUser1().getUserId())
                .build();
    }

    public static OwnerEntity someOwner1() {
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
                        .build())
                .userId(someUser1().getUserId())
                .build();
    }

    public static OwnerEntity someOwner2() {
        return OwnerEntity.builder()
                .ownerId(2L)
                .name("Wiesław")
                .surname("Stary")
                .email("wieslawstary@gmail.com")
                .phone("550660550")
                .address(AddressEntity.builder()
                        .street("Wielka 15")
                        .city("Gdańsk")
                        .postalCode("80-130")
                        .country("Polska")
                        .build())
                .userId(someUser2().getUserId())
                .build();
    }

    public static OwnerEntity someOwner3() {
        return OwnerEntity.builder()
                .ownerId(3L)
                .name("Radosław")
                .surname("Wielkopolski")
                .email("radoslawwielkopolski@gmail.com")
                .phone("518440660")
                .address(AddressEntity.builder()
                        .street("Polandia")
                        .city("Gdańsk")
                        .postalCode("80-130")
                        .country("Polska")
                        .build())
                .userId(someUser3().getUserId())
                .build();
    }

    public static OwnerEntity someOwner4() {
        return OwnerEntity.builder()
                .ownerId(4L)
                .name("Radosław")
                .surname("Wielkopolski")
                .email("radoslawwielkopolski@gmail.com")
                .phone("518440660")
                .address(AddressEntity.builder()
                        .street("Polandia")
                        .city("Gdańsk")
                        .postalCode("80-130")
                        .country("Polska")
                        .build())
                .userId(someUser4().getUserId())
                .build();
    }

    public static RestaurantEntity someRestaurant1() {
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
                .owner(someOwner1())
                .build();
    }

    public static MenuEntity someMenu1() {
        return MenuEntity.builder()
                .menuId(1L)
                .name("Moje menu")
                .description("Zapraszamy")
                .restaurant(someRestaurant1())
                .build();
    }

    public static MenuCategoryEntity someMenuCategory1() {
        return MenuCategoryEntity.builder()
                .menuCategoryId(1L)
                .name("Burgery")
                .description("Mega dobre burgerki")
                .menu(someMenu1())
                .build();
    }

    public static MenuCategoryEntity someMenuCategory2() {
        return MenuCategoryEntity.builder()
                .name("Przekąski")
                .description("Mega dobre przekąski")
                .menu(someMenu1())
                .build();
    }

    public static MenuCategoryEntity someMenuCategory3() {
        return MenuCategoryEntity.builder()
                .name("Dania obiadowe")
                .description("Mega dobre dania obiadowe")
                .menu(someMenu1())
                .build();
    }

    public static OrderItemEntity someOrderItem1() {
        return OrderItemEntity.builder()
                .orderItemId(1L)
                .unitPrice(BigDecimal.valueOf(30))
                .quantity(5)
                .categoryItem(someCategoryItem1())
                .orderRecord(someOrderRecord1())
                .build();
    }

    public static OrderRecordEntity someOrderRecord1() {
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
                .customer(someCustomer1())
                .restaurant(someRestaurant1())
                .build();
    }

    public static CategoryItemEntity someCategoryItem1() {
        return CategoryItemEntity.builder()
                .categoryItemId(1L)
                .name("Bigmac")
                .description("Soczysty burgerek")
                .price(BigDecimal.valueOf(10))
                .imageUrl("f9a79bfa-8dc3-4de1-acd2-0c902d2562c1_")
                .menuCategory(someMenuCategory1())
                .build();
    }

    public static RestaurantAddressEntity someRestaurantAddress1() {
        return RestaurantAddressEntity.builder()
                .restaurantAddressId(1L)
                .restaurant(someRestaurant1())
                .address(AddressEntity.builder()
                        .addressId(1L)
                        .street("Słowackiego 24")
                        .postalCode("70-440")
                        .city("Gdynia")
                        .country("Polska")
                        .build())
                .build();
    }

}
