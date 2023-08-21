package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, Long> {

    @Query("SELECT r FROM RestaurantEntity r JOIN FETCH r.owner WHERE r.nip = :nip")
    Optional<RestaurantEntity> findByNip(@Param("nip") String nip);

    @Query("SELECT DISTINCT r FROM RestaurantEntity r " +
            "LEFT JOIN FETCH r.menu m " +
            "LEFT JOIN FETCH m.menuCategories mc " +
            "LEFT JOIN FETCH mc.categoryItems ci")
    List<RestaurantEntity> findAllWithMenuAndCategoriesAndItems();
}
