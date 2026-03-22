package management_system.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload for user authentication (Login)")
public record LoginRequestDto(
        @Schema(description = "User's registered email address", example = "teacher@example.com") 
        String email,
        
        @Schema(description = "User's raw password", example = "password123") 
        String password
) {}