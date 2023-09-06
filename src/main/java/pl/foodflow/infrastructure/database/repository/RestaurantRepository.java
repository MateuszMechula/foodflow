package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.RestaurantDAO;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;
import pl.foodflow.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.RestaurantEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public Optional<Restaurant> findById(Long restaurantId) {
        return restaurantJpaRepository.findById(restaurantId)
                .map(restaurantEntityMapper::mapFromEntity);
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        RestaurantEntity toSave = restaurantEntityMapper.mapToEntity(restaurant);
        RestaurantEntity saved = restaurantJpaRepository.save(toSave);
        return restaurantEntityMapper.mapFromEntity(saved);
    }

    @Override
    public List<Restaurant> findAll() {
        List<RestaurantEntity> all = restaurantJpaRepository.findAll();
        List<Restaurant> list = all.stream().map(restaurantEntityMapper::mapFromEntity).toList();

        return restaurantJpaRepository.findAll().stream()
                .map(restaurantEntityMapper::mapFromEntity)
                .toList();
    }
}
