package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.OwnerDAO;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;
import pl.foodflow.infrastructure.database.repository.jpa.OwnerJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.OwnerEntityMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class OwnerRepository implements OwnerDAO {

    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerEntityMapper ownerEntityMapper;


    @Override
    public Owner saveOwner(Owner owner) {
        OwnerEntity toSave = ownerEntityMapper.mapToEntity(owner);
        OwnerEntity saved = ownerJpaRepository.save(toSave);
        return ownerEntityMapper.mapFromEntity(saved);
    }

    @Override
    public List<Owner> findAll() {
        return ownerJpaRepository.findAll().stream()
                .map(ownerEntityMapper::mapFromEntity)
                .toList();
    }
}
