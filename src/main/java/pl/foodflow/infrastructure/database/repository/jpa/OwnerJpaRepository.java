package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, Long> {

    OwnerEntity findByEmail(String email);

    List<OwnerEntity> findAll();

    Optional<OwnerEntity> findByUserId(Integer userId);

    @Query("SELECT DISTINCT owner FROM OwnerEntity owner " +
            "LEFT JOIN FETCH owner.restaurant restaurant " +
            "LEFT JOIN FETCH restaurant.menu menu " +
            "LEFT JOIN FETCH menu.menuCategories categories " +
            "LEFT JOIN FETCH categories.categoryItems " +
            "WHERE owner.userId = :userId")
    Optional<OwnerEntity> findByUserIdWithMenuAndCategoryAndItems(Integer userId);
}
