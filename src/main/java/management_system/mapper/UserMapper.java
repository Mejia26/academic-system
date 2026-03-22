package management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import management_system.dto.UserCreateDto;
import management_system.dto.UserResponseDto;
import management_system.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserResponseDto toDto(User user);
    User toEntity(UserCreateDto createDto);
}