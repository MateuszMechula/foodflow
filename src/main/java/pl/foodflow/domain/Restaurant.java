package pl.foodflow.domain;

import lombok.*;
import pl.foodflow.infrastructure.database.entity.*;

import java.math.BigDecimal;
import java.time.OffsetTime;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "nip")
@ToString(of = {"restaurantId", "name", "description", "openTime", "closeTime", "phone",
        "minimumOrderAmount", "deliveryPrice", "deliveryOption"})
public class Restaurant {

    Long restaurantId;
    String nip;
    String name;
    String description;
    OffsetTime openTime;
    OffsetTime closeTime;
    String phone;
    BigDecimal minimumOrderAmount;
    BigDecimal deliveryPrice;
    Boolean deliveryOption;
    AddressEntity address;
    OwnerEntity owner;
    MenuEntity menu;
    Set<RestaurantAddressEntity> restaurantAddresses;
    Set<RestaurantCategoryEntity> restaurantCategories;
    Set<OrderRecordEntity> orderRecords;
}
