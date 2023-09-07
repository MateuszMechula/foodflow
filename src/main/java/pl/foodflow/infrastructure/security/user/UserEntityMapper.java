package pl.foodflow.infrastructure.security.user;

import org.mapstruct.Mapper;
import pl.foodflow.api.dto.mapper.AddressMapper;

@Mapper(uses = AddressMapper.class, componentModel = "spring")
public interface UserEntityMapper {
    default User mapFromEntity(UserEntity entity) {
        return User.builder()
                .userId(entity.getUserId())
                .userName(entity.getUserName())
                .password(entity.getPassword())
                .active(entity.getActive())
                .roles(entity.getRoles())
                .build();
    }

    UserEntity mapToEntity(User user);
}
