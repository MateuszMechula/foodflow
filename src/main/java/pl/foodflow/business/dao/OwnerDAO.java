package pl.foodflow.business.dao;

import pl.foodflow.domain.Owner;

import java.util.Optional;

public interface OwnerDAO {
    Optional<Owner> findOwnerByUserId(Integer userId);

    void saveOwner(Owner owner);
}
