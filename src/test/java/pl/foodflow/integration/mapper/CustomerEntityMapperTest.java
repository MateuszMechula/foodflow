package pl.foodflow.integration.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.foodflow.domain.Customer;
import pl.foodflow.infrastructure.database.entity.CustomerEntity;
import pl.foodflow.infrastructure.database.repository.mapper.CustomerEntityMapper;
import pl.foodflow.util.TestDataForMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerEntityMapperTest {
    private CustomerEntityMapper customerEntityMapper;

    @Test
    void shouldMapCustomerToCustomerEntity() {
        //given
        Customer customer = TestDataForMappers.someCustomer();
        //when
        CustomerEntity customerEntity = customerEntityMapper.mapToEntity(customer);
        //then
        assertEquals(customerEntity.getName(), customer.getName());
        assertEquals(customerEntity.getSurname(), customer.getSurname());
        assertEquals(customerEntity.getEmail(), customer.getEmail());
        assertEquals(customerEntity.getPhone(), customer.getPhone());
        assertEquals(customerEntity.getUserId(), customer.getUserId());
    }

    @Test
    void shouldMapCustomerEntityToCustomer() {
        //given
        CustomerEntity customerEntity = TestDataForMappers.someCustomerEntity();
        //when
        Customer customer = customerEntityMapper.mapFromEntity(customerEntity);
        //then
        assertEquals(customer.getName(), customerEntity.getName());
        assertEquals(customer.getSurname(), customerEntity.getSurname());
        assertEquals(customer.getEmail(), customerEntity.getEmail());
        assertEquals(customer.getPhone(), customerEntity.getPhone());
        assertEquals(customer.getUserId(), customerEntity.getUserId());
    }
}