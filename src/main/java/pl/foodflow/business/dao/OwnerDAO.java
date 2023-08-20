package pl.foodflow.business.dao;

import pl.foodflow.domain.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerDAO {

    Owner saveOwner(Owner owner);

    List<Owner> findAll();

    Owner findByEmail(String ownerEmail);

    Optional<Owner> findById(Long ownerId);
}
