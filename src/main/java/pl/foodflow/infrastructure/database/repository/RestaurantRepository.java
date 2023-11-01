package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Optional<Restaurant> findRestaurantById(Long restaurantId) {
        return restaurantJpaRepository.findById(restaurantId)
                .map(restaurantEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<Restaurant> findRestaurantByNip(String nip) {
        return restaurantJpaRepository.findByNip(nip)
                .map(restaurantEntityMapper::mapFromEntity);
    }

    @Override
    public List<Restaurant> findAllRestaurants() {
        return restaurantJpaRepository.findAll().stream()
                .map(restaurantEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        RestaurantEntity toSave = restaurantEntityMapper.mapToEntity(restaurant);
        RestaurantEntity saved = restaurantJpaRepository.save(toSave);
        restaurantEntityMapper.mapFromEntity(saved);
    }

    @Override
    public void deleteRestaurantById(Long restaurantId) {
        restaurantJpaRepository.deleteById(restaurantId);
    }

    @Override
    public Page<Restaurant> findPaginated(Pageable pageable) {
        return restaurantJpaRepository.findAll(pageable)
                .map(restaurantEntityMapper::mapFromEntity);
    }
}
