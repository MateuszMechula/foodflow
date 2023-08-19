package pl.foodflow.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.foodflow.domain.*;

import java.math.BigDecimal;
import java.time.OffsetTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

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
}
