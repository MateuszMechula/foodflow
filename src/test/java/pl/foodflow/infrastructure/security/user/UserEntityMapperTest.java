package pl.foodflow.infrastructure.security.user;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static pl.foodflow.util.TestDataForMappers.someUser;
import static pl.foodflow.util.TestDataForMappers.someUserEntity;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
class UserEntityMapperTest {
    private UserEntityMapper userEntityMapper;

    @Test
    void shouldMapUserFromEntity() {
        //given
        UserEntity userEntity = someUserEntity();

        //when
        User user = userEntityMapper.mapFromEntity(userEntity);
        //then
        Assertions.assertEquals(userEntity.getUserName(), user.getUserName());
        Assertions.assertEquals(userEntity.getPassword(), user.getPassword());
        Assertions.assertEquals(userEntity.getActive(), user.getActive());
        Assertions.assertEquals(userEntity.getRoles(), user.getRoles());
    }

    @Test
    void shouldMapUserEntityToUser() {
        //given
        User user = someUser();
        //when
        UserEntity userEntity = userEntityMapper.mapToEntity(user);
        //then
        Assertions.assertEquals(user.getUserName(), userEntity.getUserName());
        Assertions.assertEquals(user.getPassword(), userEntity.getPassword());
        Assertions.assertEquals(user.getActive(), userEntity.getActive());
        Assertions.assertEquals(user.getRoles(), userEntity.getRoles());
    }
}