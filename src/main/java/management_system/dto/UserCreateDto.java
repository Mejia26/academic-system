package management_system.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload for registering a new user (Teacher or Admin) into the system")
public record UserCreateDto(
        @Schema(description = "First name of the user", example = "John") 
        String firstName,
        
        @Schema(description = "Last name of the user", example = "Doe") 
        String lastName,
        
        @Schema(description = "Valid email address for login and communication", example = "teacher@example.com") 
        String email,
        
        @Schema(description = "Secure password for the account", example = "SecurePass!2026") 
        String password
) {}