package pl.foodflow.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetTime;
import java.util.HashSet;
import java.util.Objects;
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
    Address address;
    Owner owner;
    Menu menu;
    Set<RestaurantAddress> restaurantAddresses;
    Set<RestaurantCategory> restaurantCategories;
    Set<OrderRecord> orderRecords;

    public Set<RestaurantAddress> getRestaurantAddresses() {
        return Objects.isNull(restaurantAddresses) ? new HashSet<>() : restaurantAddresses;
    }
}
