package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.CustomerDAO;
import pl.foodflow.business.exceptions.CustomerNotFoundException;
import pl.foodflow.domain.Customer;
import pl.foodflow.utils.ErrorMessages;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

    public Customer findCustomerById(Long customerId) {
        log.info("Fetching Customer by ID: {}", customerId);
        return customerDAO.findCustomerById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        ErrorMessages.CUSTOMER_NOT_FOUND.formatted(customerId)
                ));
    }

    public Customer getCustomerByUserId(Integer userId) {
        log.info("Fetching Customer by user ID: {}", userId);
        return customerDAO.findCustomerByUserId(userId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        ErrorMessages.CUSTOMER_WITH_USER_ID_NOT_FOUND.formatted(userId)));
    }

    @Transactional
    public void saveCustomer(Customer customer) {
        log.info("Saving Customer");
        customerDAO.saveCustomer(customer);
    }
}
