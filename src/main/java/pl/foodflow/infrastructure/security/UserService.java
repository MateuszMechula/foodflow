package pl.foodflow.infrastructure.security;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserDAO userDAO;

    @Transactional
    public User findByUserName(String userName) {
        return userDAO.findByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("Entity with username: [%s] not found".formatted(userName)));
    }
}
