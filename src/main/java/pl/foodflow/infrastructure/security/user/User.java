package pl.foodflow.infrastructure.security.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.foodflow.infrastructure.security.role.RoleEntity;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int userId;
    private String userName;
    private String password;
    private Set<RoleEntity> roles;
    private Boolean active;
}
