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

    public Owner findOwnerByUserId(Integer userId) {
        log.info("Fetching all information about the owner and his restaurant");
        return ownerDAO.findOwnerByUserId(userId)
                .orElseThrow(() -> new OwnerNotFoundException(
                        ErrorMessages.OWNER_NOT_FOUND.formatted(userId)));
    }

    @Transactional
    public void saveOwner(Owner owner) {
        log.info("Saving owner");
        ownerDAO.saveOwner(owner);
    }
}
