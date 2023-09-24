package pl.foodflow.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.business.dao.CustomerDAO;
import pl.foodflow.business.exceptions.CustomerNotFoundException;
import pl.foodflow.domain.Customer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.someCustomer1;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerDAO customerDAO;

    @Test
    void shouldReturnCustomerWhenUserIdExists() {
        //given
        Long customerId = 1L;
        Customer customer = someCustomer1();

        Mockito.when(customerDAO.findCustomerByUserId(customerId))
                .thenReturn(Optional.of(customer));
        //when
        Customer expected = customerService.getCustomerByUserId(customerId);
        //then
        assertThat(expected).isNotNull();
        assertThat(expected).isEqualTo(customer);
    }

    @Test
    void shouldThrowExceptionWhenCustomerIdDoesNotExist() {
        //given
        Long customerId = 1L;
        when(customerDAO.findCustomerByUserId(customerId)).thenReturn(Optional.empty());

        //when,then
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerByUserId(customerId));
        verify(customerDAO).findCustomerByUserId(customerId);
    }

    @Test
    void saveCustomer() {
        //given
        Customer customer = someCustomer1();
        //when
        customerService.saveCustomer(customer);
        //then
        Mockito.verify(customerDAO).saveCustomer(customer);
    }
}