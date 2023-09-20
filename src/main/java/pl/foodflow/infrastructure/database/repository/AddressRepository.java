package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.AddressDAO;
import pl.foodflow.domain.Address;
import pl.foodflow.infrastructure.database.entity.AddressEntity;
import pl.foodflow.infrastructure.database.repository.jpa.AddressJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.AddressEntityMapper;

@Repository
@AllArgsConstructor
public class AddressRepository implements AddressDAO {
    private final AddressJpaRepository addressJpaRepository;
    private final AddressEntityMapper addressEntityMapper;

    @Override
    public Address saveAddress(Address address) {
        AddressEntity toSave = addressEntityMapper.mapToEntity(address);
        AddressEntity saved = addressJpaRepository.save(toSave);

        return addressEntityMapper.mapFromEntity(saved);
    }

}
