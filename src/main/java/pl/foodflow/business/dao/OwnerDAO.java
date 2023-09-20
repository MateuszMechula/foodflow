package pl.foodflow.business.dao;

import pl.foodflow.domain.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerDAO {

    void saveOwner(Owner owner);

    List<Owner> findAll();

    Optional<Owner> findByUserId(Integer userId);

    Optional<Owner> findByUserIdWithMenuAndCategoryAndItems(int userId);
}
