package pl.foodflow.integration.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.foodflow.domain.Address;
import pl.foodflow.infrastructure.database.entity.AddressEntity;
import pl.foodflow.infrastructure.database.repository.mapper.AddressEntityMapper;
import pl.foodflow.util.TestDataForMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AddressEntityMapperTest {
    private AddressEntityMapper addressEntityMapper;

    @Test
    void shouldMapAddressToAddressEntity() {
        // Given
        Address address = TestDataForMappers.someAddress();

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
        AddressEntity addressEntity = TestDataForMappers.someAddressEntity();

        // When
        Address address = addressEntityMapper.mapFromEntity(addressEntity);

        // Then
        assertEquals(addressEntity.getStreet(), address.getStreet());
        assertEquals(addressEntity.getCity(), address.getCity());
        assertEquals(addressEntity.getPostalCode(), address.getPostalCode());
        assertEquals(addressEntity.getCountry(), address.getCountry());
    }
}