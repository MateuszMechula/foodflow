package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.OwnerDAO;
import pl.foodflow.business.exceptions.OwnerNotFoundException;
import pl.foodflow.domain.Owner;
import pl.foodflow.utils.ErrorMessages;


@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;

    public Owner findOwnerById(Long ownerId) {
        log.info("Fetching Owner by ID: {}", ownerId);
        return ownerDAO.findOwnerById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException(
                        ErrorMessages.OWNER_WITH_ID_NOT_FOUND.formatted(ownerId)));
    }

    public Owner findOwnerByUserId(Integer userId) {
        log.info("Fetching Owner by userId: {}", userId);
        return ownerDAO.findOwnerByUserId(userId)
                .orElseThrow(() -> new OwnerNotFoundException(
                        ErrorMessages.OWNER_WITH_USER_ID_NOT_FOUND.formatted(userId)));
    }

    @Transactional
    public void saveOwner(Owner owner) {
        log.info("Saving owner");
        ownerDAO.saveOwner(owner);
    }
}
