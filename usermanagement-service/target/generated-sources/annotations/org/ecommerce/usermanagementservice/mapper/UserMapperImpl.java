package org.ecommerce.usermanagementservice.mapper;

import javax.annotation.processing.Generated;
import org.ecommerce.usermanagementservice.dto.request.UserRequestDTO;
import org.ecommerce.usermanagementservice.dto.response.UserResponseDTO;
import org.ecommerce.usermanagementservice.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-27T15:32:44+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( dto.getUsername() );
        user.setEmail( dto.getEmail() );
        user.setPassword( dto.getPassword() );
        user.setPhoneNumber( dto.getPhoneNumber() );
        user.setRole( dto.getRole() );

        return user;
    }

    @Override
    public UserResponseDTO toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId( user.getId() );
        userResponseDTO.setUsername( user.getUsername() );
        userResponseDTO.setEmail( user.getEmail() );
        userResponseDTO.setPhoneNumber( user.getPhoneNumber() );
        userResponseDTO.setRole( user.getRole() );

        return userResponseDTO;
    }
}
