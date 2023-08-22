package pl.foodflow.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.OwnerDAO;
import pl.foodflow.domain.Owner;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;

    @Transactional
    public Owner findByEmail(String email) {
        return ownerDAO.findByEmail(email);
    }

    @Transactional
    public Owner findById(Long ownerId) {
        return ownerDAO.findById(ownerId).orElseThrow();
    }

    @Transactional
    public Owner findByUserId(Integer userId) {
        return ownerDAO.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "OwnerEntity with userId: [%s] not found".formatted(userId)
                ));
    }

    public List<Owner> findAll() {
        List<Owner> allOwners = ownerDAO.findAll();
        log.info("Owners : [{}]", allOwners.size());
        return allOwners;
    }

    @Transactional
    public void saveOwner(Owner owner) {
        ownerDAO.saveOwner(owner);
    }

    @Transactional
    public void update(Owner owner) {
        Owner existingOwner = ownerDAO.findById(owner.getOwnerId())
                .orElseThrow();

        Owner updatedOwner = existingOwner
                .withName(owner.getName())
                .withSurname(owner.getSurname())
                .withEmail(owner.getEmail())
                .withPhone(owner.getPhone())
                .withAddress(owner.getAddress())
                .withRestaurant(owner.getRestaurant());

        ownerDAO.saveOwner(updatedOwner);
    }

    @Transactional
    public Owner findByUserIdWithMenuAndCategoryAndItems(int userId) {
        return ownerDAO.findByUserIdWithMenuAndCategoryAndItems(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "OwnerEntity with userId: [%s] not found".formatted(userId)
                ));
    }
}
