package management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management_system.dto.UserCreateDto;
import management_system.dto.UserResponseDto;
import management_system.enums.UserRole;
import management_system.exception.DuplicateResourceException;
import management_system.exception.ResourceNotFoundException;
import management_system.mapper.UserMapper;
import management_system.model.User;
import management_system.repository.UserRepository;
import management_system.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDto createUser(UserCreateDto createDto) {
        log.info("Creating new user with email: {}", createDto.email());
        if (userRepository.existsByEmail(createDto.email())) {
            throw new DuplicateResourceException("Email is already registered");
        }

        User user = userMapper.toEntity(createDto);
        user.setPasswordHash(passwordEncoder.encode(createDto.password()));
        // Assign default role for the prototype
        user.setRole(UserRole.TEACHER); 

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        log.info("Soft-deleting user: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }
}