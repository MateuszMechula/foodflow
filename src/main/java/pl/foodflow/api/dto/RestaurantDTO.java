package pl.foodflow.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    String nip;
    String name;
    String description;
    String openTime;
    String closeTime;
    String phone;
    BigDecimal minimumOrderAmount;
    BigDecimal deliveryPrice;
    Boolean deliveryOption;
    AddressDTO address;
    String ownerEmail;

    public static RestaurantDTO buildDefault() {
        return RestaurantDTO.builder()
                .nip("7213405941")
                .name("BurgerKing")
                .description("Najlepsze Burgery")
                .openTime("10:00:00")
                .closeTime("18:00:00")
                .phone("515303202")
                .minimumOrderAmount(new BigDecimal("30"))
                .deliveryPrice(new BigDecimal("5"))
                .address(AddressDTO.builder()
                        .street("Klonowa")
                        .postalCode("11-400")
                        .city("Kętrzyn")
                        .country("Polska")
                        .build())
                .build();
    }
}
