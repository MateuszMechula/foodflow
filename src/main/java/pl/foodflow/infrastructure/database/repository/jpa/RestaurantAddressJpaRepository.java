package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.RestaurantAddressEntity;

import java.util.Optional;

@Repository
public interface RestaurantAddressJpaRepository extends JpaRepository<RestaurantAddressEntity, Long> {

    @Query("SELECT ra FROM RestaurantAddressEntity ra WHERE ra.address.addressId = :addressId")
    Optional<RestaurantAddressEntity> findByAddressId(@Param("addressId") Long addressId);
}
