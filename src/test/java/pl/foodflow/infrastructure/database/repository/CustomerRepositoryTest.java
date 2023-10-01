package pl.foodflow.infrastructure.database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.domain.Customer;
import pl.foodflow.infrastructure.database.entity.CustomerEntity;
import pl.foodflow.infrastructure.database.repository.jpa.CustomerJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.CustomerEntityMapper;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.someCustomer1;
import static pl.foodflow.util.TestDataFactory.someCustomerEntity1;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryTest {
    @InjectMocks
    private CustomerRepository customerRepository;
    @Mock
    private CustomerJpaRepository customerJpaRepository;
    @Mock
    private CustomerEntityMapper customerEntityMapper;

    @Test
    void shouldFindCustomerByUserId() {
        //given
        Integer userId = 1;
        Customer customer = someCustomer1();
        CustomerEntity customerEntity = someCustomerEntity1();

        when(customerJpaRepository.findByUserId(userId)).thenReturn(Optional.of(customerEntity));
        when(customerEntityMapper.mapFromEntity(customerEntity)).thenReturn(customer);
        //when
        Optional<Customer> customerFound = customerRepository.findCustomerByUserId(userId);
        //then
        Assertions.assertTrue(customerFound.isPresent());
        Assertions.assertEquals(customer, customerFound.get());

        verify(customerJpaRepository).findByUserId(userId);
        verify(customerEntityMapper).mapFromEntity(customerEntity);
    }

    @Test
    void shouldSaveCustomer() {
        //given
        Customer customer = someCustomer1();
        CustomerEntity customerEntity = someCustomerEntity1();

        when(customerEntityMapper.mapToEntity(customer)).thenReturn(customerEntity);
        //when
        customerRepository.saveCustomer(customer);
        //then
        verify(customerEntityMapper).mapToEntity(customer);
        verify(customerJpaRepository).save(customerEntity);
    }
}