package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.RestaurantAddressEntity;

import java.util.Optional;

@Repository
public interface RestaurantAddressJpaRepository extends JpaRepository<RestaurantAddressEntity, Long> {

    Optional<RestaurantAddressEntity> findRestaurantAddressEntityByAddressAddressId(Long addressId);
}
