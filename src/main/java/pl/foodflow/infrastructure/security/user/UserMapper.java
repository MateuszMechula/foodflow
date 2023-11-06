package pl.foodflow.infrastructure.security.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserInformationDTO mapToDTO(User savedUser);
}
