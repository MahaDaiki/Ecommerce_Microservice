package org.ecommerce.usermanagementservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ecommerce.usermanagementservice.dto.response.UserResponseDTO;
import org.ecommerce.usermanagementservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ecommerce.usermanagementservice.dto.request.UserRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO response = userService.loginUser(request);
        return ResponseEntity.ok(response);
    }
}
