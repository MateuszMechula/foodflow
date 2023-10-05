package pl.foodflow.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.entity.CustomerEntity;
import pl.foodflow.infrastructure.security.user.UserJpaRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Optional<CustomerEntity> foundCustomer = customerJpaRepository.findCustomerEntityByUserId(3);
        // then
        assertThat(foundCustomer).isPresent();
        assertEquals("Jan", foundCustomer.get().getName());
        assertEquals("Kowalski", foundCustomer.get().getSurname());
    }
}