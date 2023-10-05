package pl.foodflow.infrastructure.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.userName = :username")
    Optional<UserEntity> findUserEntityByUserName(@Param("username") String userName);
}
