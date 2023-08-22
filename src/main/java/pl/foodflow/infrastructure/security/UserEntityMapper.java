package pl.foodflow.infrastructure.security;

import org.mapstruct.Mapper;
import pl.foodflow.api.dto.mapper.AddressMapper;

@Mapper(uses = AddressMapper.class, componentModel = "spring")
public interface UserEntityMapper {
    default User map(UserEntity entity) {
        return User.builder()
                .user_id(entity.getId())
                .userName(entity.getUserName())
                .password(entity.getPassword())
                .active(entity.getActive())
                .build();
    }
}
