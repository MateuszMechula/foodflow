package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;

@Repository
public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, Long> {

    OwnerEntity findByEmail(String email);
}
