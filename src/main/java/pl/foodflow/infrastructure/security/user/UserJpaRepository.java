package pl.foodflow.infrastructure.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

    //    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.userName = :username")
    Optional<UserEntity> findUserEntityByUserName(String userName);
}
