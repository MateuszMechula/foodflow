package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.RestaurantAddressDAO;
import pl.foodflow.domain.RestaurantAddress;
import pl.foodflow.infrastructure.database.entity.RestaurantAddressEntity;
import pl.foodflow.infrastructure.database.repository.jpa.RestaurantAddressJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.RestaurantAddressMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class RestaurantAddressRepository implements RestaurantAddressDAO {
    private final RestaurantAddressJpaRepository restaurantAddressJpaRepository;
    private final RestaurantAddressMapper restaurantAddressMapper;

    @Override
    public RestaurantAddress saveRestaurantAddress(RestaurantAddress address) {
        RestaurantAddressEntity toSave = restaurantAddressMapper.mapToEntity(address);
        RestaurantAddressEntity saved = restaurantAddressJpaRepository.save(toSave);
        return restaurantAddressMapper.mapFromEntity(saved);
    }

    @Override
    public Optional<RestaurantAddress> findById(Long addressId) {
        Optional<RestaurantAddressEntity> restAddress = restaurantAddressJpaRepository.findById(addressId);

        return restAddress.map(restaurantAddressMapper::mapFromEntity);
    }

    @Override
    public void deleteRestaurantAddress(RestaurantAddress restaurantAddress) {
        RestaurantAddressEntity toDelete = restaurantAddressMapper.mapToEntity(restaurantAddress);
        restaurantAddressJpaRepository.delete(toDelete);
    }

    @Override
    public Optional<RestaurantAddress> findRestaurantAddressByAddressId(Long addressId) {
        return restaurantAddressJpaRepository.findByAddressId(addressId).map(restaurantAddressMapper::mapFromEntity);
    }
}
