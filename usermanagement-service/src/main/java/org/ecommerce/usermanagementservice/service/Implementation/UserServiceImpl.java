package org.ecommerce.usermanagementservice.service.Implementation;

import lombok.AllArgsConstructor;
import org.ecommerce.usermanagementservice.dto.request.UserRequestDTO;
import org.ecommerce.usermanagementservice.dto.response.UserResponseDTO;
import org.ecommerce.usermanagementservice.entity.User;
import org.ecommerce.usermanagementservice.mapper.UserMapper;
import org.ecommerce.usermanagementservice.repository.UserRepository;
import org.ecommerce.usermanagementservice.service.UserService;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private static final String KEYCLOAK_SERVER_URL = "http://localhost:8083/";
    private static final String REALM = "ecommerce";
    private static final String CLIENT_ID = "user-service";
    private static final String CLIENT_SECRET = "ZA7MHj3y6xDnbMJ3BRjW7DahshDkY7IR";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    @Override
    public UserResponseDTO registerUser(UserRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        User savedUser = userRepository.save(user);
        registerUserInKeycloak(request);

        return userMapper.toDto(savedUser);
    }

    @Override
    public String registerUserInKeycloak(UserRequestDTO request) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(KEYCLOAK_SERVER_URL)
                .realm("master")
                .clientId("admin-cli")
                .username(ADMIN_USERNAME)
                .password(ADMIN_PASSWORD)
                .grantType(OAuth2Constants.PASSWORD)
                .build();

        RealmResource realmResource = keycloak.realm(REALM);
        UsersResource usersResource = realmResource.users();

        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(request.getEmail());
        userRep.setEmail(request.getEmail());
        userRep.setEnabled(true);
        userRep.setEmailVerified(true);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(request.getPassword());
        passwordCred.setTemporary(false);

        userRep.setCredentials(Collections.singletonList(passwordCred));

        if (userRep.getAttributes() == null) {
            userRep.setAttributes(new HashMap<>());
        }
        userRep.getAttributes().put("phoneNumber", Collections.singletonList(request.getPhoneNumber()));

        Response response = usersResource.create(userRep);
        if (response.getStatus() == 201) {
            String keycloakUserId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            assignRoleToUser(keycloak, request.getEmail(), "User");  // Assign default role
            return keycloakUserId;
        } else {
            throw new RuntimeException("Failed to create user in Keycloak");
        }
    }

    private void assignRoleToUser(Keycloak keycloak, String username, String roleName) {
        RealmResource realmResource = keycloak.realm(REALM);
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> users = usersResource.search(username, true);

        if (users != null && !users.isEmpty()) {
            UserRepresentation user = users.get(0);
            String userId = user.getId();
            RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();
            realmResource.users().get(userId).roles().realmLevel().add(Collections.singletonList(role));
        }
    }

    @Override
    public UserResponseDTO loginUser(UserRequestDTO request) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(KEYCLOAK_SERVER_URL)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .grantType(OAuth2Constants.PASSWORD)
                .username(request.getEmail())
                .password(request.getPassword())
                .build();

        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();

        if (tokenResponse != null && tokenResponse.getToken() != null) {
            Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found");
            }

            User user = userOptional.get();
            UserResponseDTO response = userMapper.toDto(user);
            response.setToken(tokenResponse.getToken());
            return response;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
