package pl.foodflow.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.entity.CustomerEntity;
import pl.foodflow.infrastructure.security.user.UserJpaRepository;
import pl.foodflow.integration.configuration.PersistenceContainerTestConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static pl.foodflow.util.TestDataFactory.someCustomerEntity1;
import static pl.foodflow.util.TestDataFactory.someUserEntity1;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=CustomerJpaRepositoryTest")
class CustomerJpaRepositoryTest {

    private UserJpaRepository userJpaRepository;
    private CustomerJpaRepository customerJpaRepository;

    @Test
    void shouldFindByUserId() {
        // given
        userJpaRepository.saveAndFlush(someUserEntity1());
        CustomerEntity customer = someCustomerEntity1();

        customerJpaRepository.saveAndFlush(customer);
        // when
        CustomerEntity foundCustomer = customerJpaRepository.findByUserId(1).orElseThrow();
        // then
        assertNotNull(foundCustomer);
        assertEquals("Jan", foundCustomer.getName());
        assertEquals("Kowalski", foundCustomer.getSurname());
    }
}