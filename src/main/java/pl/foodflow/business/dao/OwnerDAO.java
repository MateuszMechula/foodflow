package pl.foodflow.business.dao;

import pl.foodflow.domain.Owner;

import java.util.List;

public interface OwnerDAO {

    Owner saveOwner(Owner owner);

    List<Owner> findAll();

}
