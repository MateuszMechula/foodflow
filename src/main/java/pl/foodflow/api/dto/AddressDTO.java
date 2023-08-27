package pl.foodflow.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    Long addressId;
    String street;
    String postalCode;
    String city;
    String country;

    public static AddressDTO buildDefault() {
        return AddressDTO.builder()
                .street("Klonowa")
                .postalCode("11-400")
                .city("Kętrzyn")
                .country("Polska")
                .build();
    }
}
