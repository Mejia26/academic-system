package management_system.service;

import java.util.UUID;

import management_system.dto.UserCreateDto;
import management_system.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserCreateDto createDto);
    UserResponseDto getUserById(UUID id);
    UserResponseDto getUserByEmail(String email);
    void deleteUser(UUID id);
}