package pl.foodflow.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.entity.CustomerEntity;
import pl.foodflow.infrastructure.security.user.UserJpaRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pl.foodflow.util.TestDataFactory.someCustomerEntity1;
import static pl.foodflow.util.TestDataFactory.someUserEntity1;


@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=CustomerJpaRepositoryTest")
class CustomerJpaRepositoryTest extends AbstractJpa {

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