package pl.foodflow.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.CustomerDAO;
import pl.foodflow.domain.Customer;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;
    public Customer findByUserId(Long userId) {
        return customerDAO.findByUserId(userId).orElseThrow();
    }
}
