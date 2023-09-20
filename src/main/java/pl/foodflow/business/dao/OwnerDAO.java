package pl.foodflow.business.dao;

import pl.foodflow.domain.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerDAO {
    Optional<Owner> findOwnerByUserId(Integer userId);

    List<Owner> findAllOwners();

    Optional<Owner> findByUserIdWithMenuAndCategoryAndItems(int userId);

    void saveOwner(Owner owner);
}
