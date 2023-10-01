package pl.foodflow.business.dao;

import pl.foodflow.domain.Customer;

import java.util.Optional;

public interface CustomerDAO {
    Optional<Customer> findCustomerByUserId(Integer userId);

    void saveCustomer(Customer customer);
}
