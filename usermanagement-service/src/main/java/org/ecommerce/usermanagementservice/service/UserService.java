package org.ecommerce.usermanagementservice.service;

import org.ecommerce.usermanagementservice.dto.request.UserRequestDTO;
import org.ecommerce.usermanagementservice.dto.response.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO request);
    String registerUserInKeycloak(UserRequestDTO request);
    UserResponseDTO loginUser(UserRequestDTO request);
}
