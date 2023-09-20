package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.OwnerDAO;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;
import pl.foodflow.infrastructure.database.repository.jpa.OwnerJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.OwnerEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class OwnerRepository implements OwnerDAO {

    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerEntityMapper ownerEntityMapper;


    @Override
    public Optional<Owner> findOwnerByUserId(Integer userId) {
        return ownerJpaRepository.findByUserId(userId)
                .map(ownerEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<Owner> findByUserIdWithMenuAndCategoryAndItems(int userId) {
        return ownerJpaRepository.findByUserIdWithMenuAndCategoryAndItems(userId)
                .map(ownerEntityMapper::mapFromEntity);
    }

    @Override
    public void saveOwner(Owner owner) {
        OwnerEntity toSave = ownerEntityMapper.mapToEntity(owner);
        ownerJpaRepository.save(toSave);
    }
}
