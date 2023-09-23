package pl.foodflow.infrastructure.database.repository.jpa;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, Long> {
    @NonNull List<OwnerEntity> findAll();
    Optional<OwnerEntity> findOwnerEntityByUserId(Integer userId);
}
