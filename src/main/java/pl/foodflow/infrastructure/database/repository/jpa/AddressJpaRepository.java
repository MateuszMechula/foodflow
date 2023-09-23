package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.AddressEntity;

import java.util.Set;

@Repository
public interface AddressJpaRepository extends JpaRepository<AddressEntity, Long> {
}
