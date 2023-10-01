package pl.foodflow.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.foodflow.domain.Address;
import pl.foodflow.infrastructure.database.entity.AddressEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.foodflow.util.TestDataForMappers.someAddress;
import static pl.foodflow.util.TestDataForMappers.someAddressEntity;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AddressEntityMapperTest {
    private AddressEntityMapper addressEntityMapper;

    @Test
    void shouldMapAddressToAddressEntity() {
        // Given
        Address address = someAddress();

        // When
        AddressEntity addressEntity = addressEntityMapper.mapToEntity(address);

        // Then
        assertEquals(address.getStreet(), addressEntity.getStreet());
        assertEquals(address.getCity(), addressEntity.getCity());
        assertEquals(address.getPostalCode(), addressEntity.getPostalCode());
        assertEquals(address.getCountry(), addressEntity.getCountry());
    }

    @Test
    void shouldMapAddressEntityToAddress() {
        // Given
        AddressEntity addressEntity = someAddressEntity();

        // When
        Address address = addressEntityMapper.mapFromEntity(addressEntity);

        // Then
        assertEquals(addressEntity.getStreet(), address.getStreet());
        assertEquals(addressEntity.getCity(), address.getCity());
        assertEquals(addressEntity.getPostalCode(), address.getPostalCode());
        assertEquals(addressEntity.getCountry(), address.getCountry());
    }
}