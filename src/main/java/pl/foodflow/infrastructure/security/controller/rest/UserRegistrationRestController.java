package pl.foodflow.infrastructure.security.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.foodflow.infrastructure.security.role.RoleEnum;
import pl.foodflow.infrastructure.security.user.*;

@RestController
@AllArgsConstructor
@Tag(name = "user")
public class UserRegistrationRestController {

    public static final String USERS = "/api/v1/users";
    public static final String USER_ID = "/{userId}";
    public static final String REGISTER = "/register";
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get user")
    @GetMapping(value = USERS + USER_ID)
    public ResponseEntity<UserInformationDTO> getUser(@PathVariable int userId) {
        User user = userService.findById(userId);
        UserInformationDTO userInformationDTO = userMapper.mapToDTO(user);
        return ResponseEntity.status(HttpStatus.OK).body(userInformationDTO);
    }

    @Operation(summary = "Register user")
    @PostMapping(value = USERS + REGISTER)
    public ResponseEntity<UserInformationDTO> registerUser(
            @RequestBody UserDTO userDTO,
            @RequestParam RoleEnum role) {

        userDTO.setRole(role.toString());
        User savedUser = userService.registerUser(userDTO);
        UserInformationDTO userInformation = userMapper.mapToDTO(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userInformation);
    }
}
