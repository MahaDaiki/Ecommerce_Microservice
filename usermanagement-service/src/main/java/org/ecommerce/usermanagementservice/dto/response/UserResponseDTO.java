package org.ecommerce.usermanagementservice.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Integer id;
    private String username;
    private String email;
    private String phoneNumber;
    private String role;
    private String token;
}
