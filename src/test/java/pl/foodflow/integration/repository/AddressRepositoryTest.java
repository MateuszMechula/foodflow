package pl.foodflow.integration.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.domain.Address;
import pl.foodflow.infrastructure.database.repository.AddressRepository;
import pl.foodflow.integration.configuration.PersistenceContainerTestConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.foodflow.util.TestDataFactory.someAddress1;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=AddressRepositoryTest")
class AddressRepositoryTest {
    private AddressRepository addressRepository;

    @Test
    void shouldSaveAddressToDatabase() {
        // Given
        Address address = someAddress1();

        // When
        Address savedAddress = addressRepository.saveAddress(address);

        // Then
        Optional<Address> retrievedAddressOptional = addressRepository.findById(savedAddress.getAddressId());
        assertTrue(retrievedAddressOptional.isPresent());
        Address retrievedAddress = retrievedAddressOptional.get();
        assertEquals(address.getStreet(), retrievedAddress.getStreet());
        assertEquals(address.getCity(), retrievedAddress.getCity());
        assertEquals(address.getPostalCode(), retrievedAddress.getPostalCode());
        assertEquals(address.getCountry(), retrievedAddress.getCountry());
    }
}