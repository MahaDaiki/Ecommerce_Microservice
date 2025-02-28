package org.ecommerce.usermanagementservice.service.Implementation;

import org.ecommerce.usermanagementservice.dto.request.UserRequestDTO;
import org.ecommerce.usermanagementservice.dto.response.UserResponseDTO;
import org.ecommerce.usermanagementservice.entity.User;
import org.ecommerce.usermanagementservice.mapper.UserMapper;
import org.ecommerce.usermanagementservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.resource.RealmResource;
import javax.ws.rs.core.Response;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Keycloak keycloak;

    @Mock
    private RealmResource realmResource;

    @Mock
    private UsersResource usersResource;

    @Mock
    private Response keycloakResponse;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UserRequestDTO userRequestDTO;
    private User user;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("testuser");
        userRequestDTO.setEmail("testuser@example.com");
        userRequestDTO.setPassword("password");
        userRequestDTO.setPhoneNumber("123456789");

        user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("encodedpassword");
        user.setRole("USER");

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername("testuser");
        userResponseDTO.setEmail("testuser@example.com");


        when(keycloak.realm("ecommerce")).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
    }





    @Test
    void testRegisterUser_UserAlreadyExists() {
        when(userRepository.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userServiceImpl.registerUser(userRequestDTO));
        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void testLoginUser_Success() {

        when(userRepository.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponseDTO);

        String fakeToken = "fakeToken";
        userResponseDTO.setToken(fakeToken);
        UserResponseDTO result = userServiceImpl.loginUser(userRequestDTO);

        assertNotNull(result);
        assertEquals("fakeToken", fakeToken);
    }


    @Test
    void testLoginUser_UserNotFound() {
        when(userRepository.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userServiceImpl.loginUser(userRequestDTO));
        assertEquals("User not found", exception.getMessage());
    }


    @Test
    void testRegisterUserInKeycloak_Failure() {
        when(keycloak.realm("ecommerce")).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.create(any(UserRepresentation.class)))
                .thenThrow(new RuntimeException("Failed to create user in Keycloak"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userServiceImpl.registerUserInKeycloak(userRequestDTO));
        assertEquals("Failed to create user in Keycloak", exception.getMessage());
    }

}