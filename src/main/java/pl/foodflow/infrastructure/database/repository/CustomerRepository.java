package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.CustomerDAO;
import pl.foodflow.domain.Customer;
import pl.foodflow.infrastructure.database.repository.jpa.CustomerJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.CustomerEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerEntityMapper customerEntityMapper;


    @Override
    public Optional<Customer> findByUserId(Long userId) {
        return customerJpaRepository.findByUserId(userId)
                .map(customerEntityMapper::mapFromEntity);
    }
}
