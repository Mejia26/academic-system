package management_system.dto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

import management_system.enums.UserRole;

@Schema(description = "Response object containing public user profile information. Password hash is strictly excluded.")
public record UserResponseDto(
        @Schema(description = "Unique identifier of the user", example = "111e4567-e89b-12d3-a456-426614174000") 
        UUID id,
        
        @Schema(description = "First name of the user", example = "John") 
        String firstName,
        
        @Schema(description = "Last name of the user", example = "Doe") 
        String lastName,
        
        @Schema(description = "Email address of the user", example = "teacher@example.com") 
        String email,
        
        @Schema(description = "System role defining user permissions", example = "TEACHER") 
        UserRole role
) {}