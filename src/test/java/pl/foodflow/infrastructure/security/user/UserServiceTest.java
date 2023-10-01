package pl.foodflow.infrastructure.security.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.foodflow.business.CustomerService;
import pl.foodflow.business.OwnerService;
import pl.foodflow.domain.Owner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pl.foodflow.util.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserDAO userDAO;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private OwnerService ownerService;
    @Mock
    private CustomerService customerService;

    @Test
    void shouldFindUserByUsername() {
        //given
        String username = "testUser";
        User expectedUser = someUser1();

        when(userDAO.findByUserName(username)).thenReturn(Optional.ofNullable(expectedUser));
        //when
        User result = userService.findByUsername(username);
        //then
        Assertions.assertEquals(expectedUser, result);
    }

    @Test
    void shouldGetUserIdByAuth() {
        //given
        String username = "testUser";
        User user = someUser1();
        Integer expectedUserId = 1;

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getName()).thenReturn(username);
        when(userDAO.findByUserName(username)).thenReturn(Optional.ofNullable(user));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        //when
        Integer result = userService.getUserIdByAuth();

        //then
        assertEquals(expectedUserId, result);
    }

    @Test
    void shouldGetUsernameFromAuth() {
        //given
        String username = "testUser";
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getName()).thenReturn(username);
        //when
        String result = userService.getUsernameFromAuth();

        //then
        assertEquals(username, result);
    }

    @Test
    void shouldGetCurrentOwner() {
        //given
        String username = "testUser";
        Owner expectedOwner = someOwner1();
        User user = someUser1();
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getName()).thenReturn(username);
        when(userDAO.findByUserName(username)).thenReturn(Optional.ofNullable(user));
        when(ownerService.findOwnerByUserId(1)).thenReturn(expectedOwner);
        //when
        Owner result = userService.getCurrentOwner();
        //then
        assertEquals(expectedOwner, result);
    }

    @Test
    void shouldRegisterUserOwner() {
        //given
        UserDTO userDTO = someUserDTO1();
        User user = someUser1();

        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("test");
        when(userDAO.save(any())).thenReturn(user);
        //when
        userService.registerUser(userDTO);
        //then
        verify(userDAO, times(1)).save(any());
        verify(ownerService, times(1)).saveOwner(any());
    }

    @Test
    void shouldRegisterUserCustomer() {
        //given
        UserDTO userDTO = someUserDTO2();
        User user = someUser1();

        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("test");
        when(userDAO.save(any())).thenReturn(user);
        //when
        userService.registerUser(userDTO);
        //then
        verify(userDAO, times(1)).save(any());
        verify(customerService, times(1)).saveCustomer(any());
    }
}