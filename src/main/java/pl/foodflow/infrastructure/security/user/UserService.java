package pl.foodflow.infrastructure.security.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.foodflow.business.CustomerService;
import pl.foodflow.business.OwnerService;
import pl.foodflow.domain.Address;
import pl.foodflow.domain.Customer;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.security.role.RoleEntity;
import pl.foodflow.infrastructure.security.role.RoleEnum;
import pl.foodflow.utils.ErrorMessages;

import java.util.Collections;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final OwnerService ownerService;
    private final CustomerService customerService;

    public User findByUsername(String userName) {
        return userDAO.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException(
                        ErrorMessages.USER_WITH_USERNAME_NOT_FOUND.formatted(userName)));
    }

    public Integer getUserIdByAuth() {
        String username = getUsernameFromAuth();
        return findByUsername(username).getUserId();
    }

    public String getUsernameFromAuth() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Owner getCurrentOwner() {
        int userIdByAuth = getUserIdByAuth();
        return ownerService.findOwnerByUserId(userIdByAuth);
    }

    @Transactional
    public void registerUser(UserDTO userDTO) {
        User user = buildUser(userDTO);
        User savedUser = userDAO.save(user);

        if (userDTO.getRole().equalsIgnoreCase("owner")) {
            Owner owner = buildNewOwner(userDTO);
            ownerService.saveOwner(owner.withUserId(savedUser.getUserId()));
        } else if (userDTO.getRole().equalsIgnoreCase("customer")) {
            Customer customer = buildNewCustomer(userDTO);
            customerService.saveCustomer(customer.withUserId(savedUser.getUserId()));
        }
        log.info("User registered successfully: {}", userDTO.getUsername());
    }

    private Customer buildNewCustomer(UserDTO userDTO) {
        return Customer.builder()
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .address(Address.builder()
                        .street(userDTO.getStreet())
                        .city(userDTO.getCity())
                        .postalCode(userDTO.getPostalCode())
                        .country(userDTO.getCountry())
                        .build())
                .build();
    }

    private Owner buildNewOwner(UserDTO userDTO) {
        return Owner.builder()
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .address(Address.builder()
                        .street(userDTO.getStreet())
                        .city(userDTO.getCity())
                        .postalCode(userDTO.getPostalCode())
                        .country(userDTO.getCountry())
                        .build())
                .build();
    }

    private User buildUser(UserDTO userDTO) {
        return User.builder()
                .userName(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .active(true)
                .roles(assignRoles(userDTO.getRole()))
                .build();
    }

    private Set<RoleEntity> assignRoles(String role) {
        if (role.equalsIgnoreCase(RoleEnum.OWNER.toString())) {
            return Set.of(RoleEntity.builder()
                    .roleId(1)
                    .role(RoleEnum.OWNER.toString())
                    .build());
        } else if (role.equalsIgnoreCase(RoleEnum.CUSTOMER.toString())) {
            return Set.of(RoleEntity.builder()
                    .roleId(2)
                    .role(RoleEnum.CUSTOMER.toString())
                    .build());
        }
        return Collections.emptySet();
    }
}
