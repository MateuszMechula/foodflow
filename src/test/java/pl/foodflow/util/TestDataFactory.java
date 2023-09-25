package pl.foodflow.util;

import pl.foodflow.api.dto.OrderDTO;
import pl.foodflow.api.dto.SearchAddressDTO;
import pl.foodflow.domain.*;
import pl.foodflow.enums.DeliveryType;
import pl.foodflow.enums.OrderStatus;
import pl.foodflow.infrastructure.database.entity.*;
import pl.foodflow.infrastructure.security.user.User;
import pl.foodflow.infrastructure.security.user.UserEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestDataFactory {

    public static UserEntity someUserEntity1() {
        return UserEntity.builder()
                .userId(1)
                .userName("user1")
                .password("test")
                .active(true)
                .build();
    }

    public static UserEntity someUserEntity2() {
        return UserEntity.builder()
                .userId(2)
                .userName("user2")
                .password("test")
                .active(true)
                .build();
    }

    public static UserEntity someUserEntity3() {
        return UserEntity.builder()
                .userId(3)
                .userName("user3")
                .password("test")
                .active(true)
                .build();
    }

    public static UserEntity someUserEntity4() {
        return UserEntity.builder()
                .userId(4)
                .userName("user3")
                .password("test")
                .active(true)
                .build();
    }

    public static User someUser1() {
        return User.builder()
                .userId(1)
                .userName("user1")
                .password("test")
                .active(true)
                .build();
    }

    public static CustomerEntity someCustomerEntity1() {
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
                .userId(someUserEntity1().getUserId())
                .build();
    }

    public static Customer someCustomer1() {
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
                .userId(someUser1().getUserId())
                .build();
    }

    public static OwnerEntity someOwnerEntity1() {
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
                .userId(someUserEntity1().getUserId())
                .build();
    }
    public static Owner someOwner1() {
        return Owner.builder()
                .ownerId(1L)
                .name("Patryk")
                .surname("Nowobogacki")
                .email("patryknowobogacki@gmail.com")
                .phone("604555005")
                .address(Address.builder()
                        .street("Słowackiego")
                        .city("Gdańsk")
                        .postalCode("80-130")
                        .country("Polska")
                        .build())
                .userId(someUser1().getUserId())
                .restaurant(someRestaurant1())
                .build();
    }
    public static Owner someOwner2() {
        return Owner.builder()
                .ownerId(1L)
                .name("Patryk")
                .surname("Nowobogacki")
                .email("patryknowobogacki@gmail.com")
                .phone("604555005")
                .address(Address.builder()
                        .street("Słowackiego")
                        .city("Gdańsk")
                        .postalCode("80-130")
                        .country("Polska")
                        .build())
                .userId(someUser1().getUserId())
                .restaurant(someRestaurant2())
                .build();
    }
    public static Owner someOwnerWithoutRestaurant1() {
        return Owner.builder()
                .ownerId(1L)
                .name("Patryk")
                .surname("Nowobogacki")
                .email("patryknowobogacki@gmail.com")
                .phone("604555005")
                .address(Address.builder()
                        .street("Słowackiego")
                        .city("Gdańsk")
                        .postalCode("80-130")
                        .country("Polska")
                        .build())
                .userId(someUser1().getUserId())
                .build();
    }

    public static OwnerEntity someOwnerEntity2() {
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
                .userId(someUserEntity2().getUserId())
                .build();
    }

    public static OwnerEntity someOwnerEntity3() {
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
                .userId(someUserEntity3().getUserId())
                .build();
    }

    public static OwnerEntity someOwnerEntity4() {
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
                .userId(someUserEntity4().getUserId())
                .build();
    }

    public static RestaurantEntity someRestaurantEntity1() {
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
                .owner(someOwnerEntity1())
                .build();
    }

    public static Restaurant someRestaurant1() {
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
                .menu(someMenu1())
                .build();
    }
    public static Restaurant someRestaurant2() {
        return Restaurant.builder()
                .restaurantId(1L)
                .nip("7244505040")
                .name("Gospoda")
                .description("Najlepsza restauracja w trójmieście")
                .openTime(OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC))
                .closeTime(OffsetTime.of(22, 30, 0, 0, ZoneOffset.UTC))
                .phone("515273444")
                .minimumOrderAmount(BigDecimal.valueOf(30))
                .deliveryPrice(BigDecimal.valueOf(6))
                .deliveryOption(true)
                .address(Address.builder()
                        .street("Orzechowa")
                        .postalCode("80-300")
                        .city("Gdańsk")
                        .country("Polska")
                        .build())
                .build();
    }

    public static MenuEntity someMenuEntity1() {
        return MenuEntity.builder()
                .menuId(1L)
                .name("Moje menu")
                .description("Zapraszamy")
                .restaurant(someRestaurantEntity1())
                .build();
    }
    public static Menu someMenu1() {
        return Menu.builder()
                .menuId(1L)
                .name("Best menu")
                .description("Zapraszamy")
                .build();
    }

    public static MenuCategoryEntity someMenuCategoryEntity1() {
        return MenuCategoryEntity.builder()
                .menuCategoryId(1L)
                .name("Burgery")
                .description("Mega dobre burgerki")
                .menu(someMenuEntity1())
                .build();
    }

    public static MenuCategoryEntity someMenuCategoryEntity2() {
        return MenuCategoryEntity.builder()
                .name("Przekąski")
                .description("Mega dobre przekąski")
                .menu(someMenuEntity1())
                .build();
    }

    public static MenuCategoryEntity someMenuCategoryEntity3() {
        return MenuCategoryEntity.builder()
                .name("Dania obiadowe")
                .description("Mega dobre dania obiadowe")
                .menu(someMenuEntity1())
                .build();
    }

    public static OrderItemEntity someOrderItemEntity1() {
        return OrderItemEntity.builder()
                .orderItemId(1L)
                .unitPrice(BigDecimal.valueOf(30))
                .quantity(5)
                .categoryItem(someCategoryItemEntity1())
                .orderRecord(someOrderRecordEntity1())
                .build();
    }

    public static OrderItem someOrderItem1() {
        return OrderItem.builder()
                .orderItemId(1L)
                .unitPrice(BigDecimal.valueOf(30))
                .quantity(5)
                .categoryItem(someCategoryItem1())
                .orderRecord(someOrderRecord1())
                .build();
    }

    public static OrderRecordEntity someOrderRecordEntity1() {
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
                .customer(someCustomerEntity1())
                .restaurant(someRestaurantEntity1())
                .build();
    }

    public static OrderRecord someOrderRecord1() {
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
                .customer(someCustomer1())
                .restaurant(someRestaurant1())
                .build();
    }
    public static OrderRecord someUpdatedOrderRecord1() {
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
                .customer(someCustomer1())
                .restaurant(someRestaurant1())
                .build();
    }
    public static OrderRecord someOrderRecord2() {
        return OrderRecord.builder()
                .orderRecordId(2L)
                .orderNumber("ORDER16")
                .orderDateTime(OffsetDateTime.of(2020, 6, 6, 0, 0, 0, 0, ZoneOffset.UTC))
                .orderStatus(OrderStatus.COMPLETED.toString())
                .orderNotes("proszę o szybką dostawę")
                .totalAmount(BigDecimal.valueOf(30))
                .contactPhone("606550660")
                .deliveryAddress("Wolska 13, 11-500 Giżycko")
                .deliveryType(DeliveryType.DELIVERY.toString())
                .customer(someCustomer1())
                .restaurant(someRestaurant1())
                .build();
    }
    public static OrderRecord someOrderRecord3() {
        return OrderRecord.builder()
                .orderRecordId(3L)
                .orderNumber("ORDER17")
                .orderDateTime(OffsetDateTime.of(2021, 6, 6, 0, 0, 0, 0, ZoneOffset.UTC))
                .orderStatus(OrderStatus.IN_PROGRESS.toString())
                .orderNotes("proszę o szybką dostawę")
                .totalAmount(BigDecimal.valueOf(30))
                .contactPhone("607005444")
                .deliveryAddress("Glancka 15, 67-446 Raki")
                .deliveryType(DeliveryType.DELIVERY.toString())
                .customer(someCustomer1())
                .restaurant(someRestaurant1())
                .build();
    }

    public static CategoryItemEntity someCategoryItemEntity1() {
        return CategoryItemEntity.builder()
                .categoryItemId(1L)
                .name("Bigmac")
                .description("Soczysty burgerek")
                .price(BigDecimal.valueOf(10))
                .imageUrl("f9a79bfa-8dc3-4de1-acd2-0c902d2562c1_")
                .menuCategory(someMenuCategoryEntity1())
                .build();
    }

    public static CategoryItem someCategoryItem1() {
        return CategoryItem.builder()
                .categoryItemId(1L)
                .name("Bigmac")
                .description("Soczysty burgerek")
                .price(BigDecimal.valueOf(10))
                .imageUrl("url")
                .build();
    }

    public static CategoryItem someCategoryItem2() {
        return CategoryItem.builder()
                .categoryItemId(1L)
                .name("McChicken")
                .description("Soczysty burgerek")
                .price(BigDecimal.valueOf(10))
                .imageUrl("f9a79bfa-8dc3-4de1-acd2-0c902d2562c1_")
                .build();
    }

    public static RestaurantAddressEntity someRestaurantAddressEntity1() {
        return RestaurantAddressEntity.builder()
                .restaurantAddressId(1L)
                .restaurant(someRestaurantEntity1())
                .address(AddressEntity.builder()
                        .addressId(1L)
                        .street("Słowackiego 24")
                        .postalCode("70-440")
                        .city("Gdynia")
                        .country("Polska")
                        .build())
                .build();
    }
    public static RestaurantAddress someRestaurantAddress1() {
        return RestaurantAddress.builder()
                .restaurantAddressId(1L)
                .restaurant(someRestaurant1())
                .address(Address.builder()
                        .addressId(1L)
                        .street("klonowa")
                        .postalCode("11-500")
                        .city("Giżycko")
                        .country("Polska")
                        .build())
                .build();
    }
    public static RestaurantAddress someRestaurantAddress2() {
        return RestaurantAddress.builder()
                .restaurantAddressId(1L)
                .restaurant(someRestaurant1())
                .address(Address.builder()
                        .addressId(2L)
                        .street("Romana 24")
                        .postalCode("70-660")
                        .city("Gdynia")
                        .country("Polska")
                        .build())
                .build();
    }

    public static Address someAddress1() {
        return Address.builder()
                .country("Polska")
                .street("Wyzwolenia")
                .postalCode("30-440")
                .city("Wałbrzych")
                .build();
    }

    public static MenuCategory someMenuCategory1() {
        return MenuCategory.builder()
                .menuCategoryId(1L)
                .name("Burgery")
                .description("Dobre burgerki")
                .menu(someMenu1())
                .build();
    }

    public static MenuCategory someMenuCategory2() {
        return MenuCategory.builder()
                .name("Pizza")
                .description("Dobra pizza")
                .menu(someMenu1())
                .build();
    }

    public static MenuCategory someMenuCategory3() {
        return MenuCategory.builder()
                .name("Pierogi")
                .description("Dobre pierożki")
                .menu(someMenu1())
                .build();
    }

    public static SearchAddressDTO someSearchAddressDTO() {
        return SearchAddressDTO.builder()
                .street("Klonowa")
                .postalCode("11-500")
                .city("Giżycko")
                .build();
    }
    public static OrderDTO someOrderDTO() {
        Map<Long, Integer> orderItems = new HashMap<>();
        orderItems.put(1L, 2);
        return OrderDTO.builder()
                .orderNotes("Proszę o szybką dostawę")
                .orderItems(orderItems)
                .contactPhone("525404033")
                .deliveryAddress("Romanowskiego 15, 11-500 Giżycko")
                .deliveryType(DeliveryType.DELIVERY)
                .build();
    }
    public static OrderDTO someEmptyOrderDTO() {
        return OrderDTO.builder()
                .orderNotes("Proszę o szybką dostawę")
                .orderItems(Collections.emptyMap())
                .contactPhone("525404033")
                .deliveryAddress("Romanowskiego 15, 11-500 Giżycko")
                .deliveryType(DeliveryType.DELIVERY)
                .build();
    }

}
