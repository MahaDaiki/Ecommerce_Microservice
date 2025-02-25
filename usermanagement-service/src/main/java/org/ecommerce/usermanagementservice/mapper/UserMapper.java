package org.ecommerce.usermanagementservice.mapper;

import org.ecommerce.usermanagementservice.dto.request.UserRequestDTO;
import org.ecommerce.usermanagementservice.dto.response.UserResponseDTO;
import org.ecommerce.usermanagementservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toDto(User user);
}
